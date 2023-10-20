package SD94.service.serviceImplement;

import SD94.controller.Message.Message;
import SD94.entity.Cart;
import SD94.entity.Customer;
import SD94.repository.CartRepository;
import SD94.repository.CustomerRepository;
import SD94.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomerServceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CartRepository cartRepository;

    @Override
    public List<Customer> findAllCustomer() {
        List<Customer> customers = customerRepository.findAllCustomer();
        return customers;
    }

    @Override
    public ResponseEntity<Customer> createCustomer(Customer customerCreate) {
        Optional<Customer> optionalCustomer = customerRepository.findByName(customerCreate.getName());
        String errorMessage;
        Message errprResponse;

        if (optionalCustomer.isPresent()){
            errorMessage = "trùng mã khách hàng";
            errprResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errprResponse, HttpStatus.BAD_REQUEST);
        }

        if (customerCreate.getName() == null || customerCreate.getPhoneNumber() == null || customerCreate.getEmail() == null
                || customerCreate.getDateBirth() == null || customerCreate.getAddRess() == null || customerCreate.getPassWord() == null){
            errorMessage = "Nhập thông tin đầy đủ";
            errprResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errprResponse, HttpStatus.BAD_REQUEST);
        }

        // SDT
        if (customerCreate.getPhoneNumber().length() != 10){
            errorMessage = "Số điện thoại phải đủ 10 số";
            errprResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errprResponse, HttpStatus.BAD_REQUEST);
        }

        //Email
        String email = customerCreate.getEmail();
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";//kiểm tra định dạng email
        Pattern pattern = Pattern.compile(emailRegex);//tạo Pattern để kiểm tra email
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()){
            errorMessage = "Địa chỉ Eamil không đúng định dạng";
            errprResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errprResponse, HttpStatus.BAD_REQUEST);
        }

        Date currentDate = new Date();
        Date dateOfBirth = customerCreate.getDateBirth();

        if (dateOfBirth.after(currentDate)) {
            errorMessage = "Ngày sinh không thể nằm ở tương lai";
            errprResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errprResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            Customer customer = new Customer();
            customer.setName(customerCreate.getName());
            customer.setPhoneNumber(customerCreate.getPhoneNumber());
            customer.setEmail(customerCreate.getEmail());
            customer.setDateBirth(customerCreate.getDateBirth());
            customer.setAddRess(customerCreate.getAddRess());
            customer.setPassWord(customerCreate.getPassWord());
            customerRepository.save(customer);

            Cart cart = new Cart();
            cart.setCustomer(customer);
            cartRepository.save(cart);
            return ResponseEntity.ok(customer);
        }catch (Exception e){
            return new ResponseEntity(new Message(e.getMessage(), TrayIcon.MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Customer> editCustomer(Customer customerEdit) {
        String errorMessage;
        Message errprResponse;

        if (customerEdit.getName() == null || customerEdit.getPhoneNumber() == null || customerEdit.getEmail() == null
                || customerEdit.getDateBirth() == null || customerEdit.getAddRess() == null || customerEdit.getPassWord() == null){
            errorMessage = "Nhập thông tin đầy đủ";
            errprResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errprResponse, HttpStatus.BAD_REQUEST);
        }

        // SDT
//        String phoneNumber = customerCreate.getPhoneNumber();
        if (customerEdit.getPhoneNumber().length() != 10){
            errorMessage = "Số điện thoại phải đủ 10 số";
            errprResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errprResponse, HttpStatus.BAD_REQUEST);
        }

        //Email
        String email = customerEdit.getEmail();
        String emailRegex = "^[A-Za-z0-9+_.-]+@gmail(.+)$";//kiểm tra định dạng email
        Pattern pattern = Pattern.compile(emailRegex);//tạo Pattern để kiểm tra email
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()){
            errorMessage = "Địa chỉ Eamil không đúng định dạng";
            errprResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errprResponse, HttpStatus.BAD_REQUEST);
        }

        //Ngày sinh
        Date dateBirth = customerEdit.getDateBirth();
        Date dateC = new Date();
        if (dateBirth.after(dateC)){
            errorMessage = "Ngày sinh không vượt quá thời gian hiện tại";
            errprResponse = new Message(errorMessage, TrayIcon.MessageType.ERROR);
            return new ResponseEntity(errprResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            Optional<Customer> optionalCustomer = customerRepository.findById(customerEdit.getId());
            if (optionalCustomer.isPresent()){
                Customer customer = optionalCustomer.get();
                customer.setName(customerEdit.getName());
                customer.setPhoneNumber(customerEdit.getPhoneNumber());
                customer.setEmail(customerEdit.getEmail());
                customer.setDateBirth(customerEdit.getDateBirth());
                customer.setAddRess(customerEdit.getAddRess());
                customer.setPassWord(customerEdit.getPassWord());
                customerRepository.save(customer);
                return ResponseEntity.ok(customer);
            }else {
                return ResponseEntity.notFound().build();
            }

        }catch (Exception e){
            return new ResponseEntity(new Message(e.getMessage(), TrayIcon.MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<Customer>> deleteCustomer(Long id) {
        try {
            Optional<Customer> optionalCustomer = customerRepository.findById(id);
            if (optionalCustomer.isPresent()){
                Customer customer = optionalCustomer.get();
                customer.setDeleted(true);
                customerRepository.save(customer);

                List<Customer> customerList = findAllCustomer();
                return ResponseEntity.ok(customerList);
            }else {
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Override
    public List<Customer> searchAllCustomer(String search) {
        List<Customer> customerList = customerRepository.findCustomerAll(search);
        return customerList;
    }

    @Override
    public List<Customer> searchDateCustomer(String searchDate) {
        LocalDate searchdate = LocalDate.parse(searchDate);
        List<Customer> customerList = customerRepository.findCustomerDate(searchdate);
        return customerList;
    }
}