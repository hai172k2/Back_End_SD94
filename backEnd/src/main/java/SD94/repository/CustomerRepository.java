package SD94.repository;

import SD94.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "select * from customer where is_deleted = false ORDER BY id desc", nativeQuery = true)
    List<Customer> findAllCustomer();

    @Query(value = "select * from customer where id = ? and is_deleted = false", nativeQuery = true)
    Customer findByID(Long id);

    @Query(value = "select * from customer where name = ?", nativeQuery = true)
    Optional<Customer> findByName(String name);

    @Query(value = "select * from customer where name = ?1 OR phone_number = ?1 OR email = ?1 OR add_ress = ?1", nativeQuery = true)
    List<Customer> findCustomerAll(String input);

    @Query(value = "select * from customer where is_deleted = false and date(date_birth) = ?", nativeQuery = true)
    List<Customer> findCustomerDate(LocalDate dateBirth);
}