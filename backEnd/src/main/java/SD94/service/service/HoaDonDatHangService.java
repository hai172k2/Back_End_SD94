package SD94.service.service;

import SD94.entity.hoaDon.HoaDon;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface HoaDonDatHangService {
    List<HoaDon> findHoaDonByTrangThai(long trang_thai_id);

    ResponseEntity<Map<String, Boolean>> capNhatTrangThai(long trang_thai_id, long id_bill);

    ResponseEntity<Map<String, Boolean>> capNhatTrangThai_TatCa(long trang_thai_id, long trang_thai_id_sau);
    ResponseEntity<Map<String, Boolean>> capNhatTrangThai_DaChon(List<String> id,long trang_thai_id);

    List<HoaDon> searchAllBill(long trang_thai_id, String search);

    List<HoaDon> searchDateBill(long trang_thai_id, String searchDate);
}
