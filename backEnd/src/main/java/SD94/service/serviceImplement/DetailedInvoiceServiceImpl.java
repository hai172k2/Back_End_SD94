package SD94.service.serviceImplement;



import SD94.entity.BillDetails;
import SD94.repository.BillDetailsRepository;
import SD94.service.DetailedInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailedInvoiceServiceImpl implements DetailedInvoiceService {

    @Autowired
    BillDetailsRepository billDetailsRepository;


    @Override
    public List<BillDetails> findByIDBill(Long id) {
        List<BillDetails> billDetail = billDetailsRepository.findByIDBill(id);
        return billDetail;
    }


}
