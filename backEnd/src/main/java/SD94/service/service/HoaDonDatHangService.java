package SD94.service.service;

import SD94.dto.HoaDonDTO;
import SD94.entity.hoaDon.HoaDon;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface HoaDonDatHangService {
    List<HoaDon> findHoaDonByTrangThai(long trang_thai_id);

    ResponseEntity<Map<String, Boolean>> capNhatTrangThai(long trang_thai_id, long id_bill);

    ResponseEntity<Map<String, Boolean>> capNhatTrangThai_TatCa(long trang_thai_id, long trang_thai_id_sau, String thaoTac, String nguoiThaoTac);
    List<HoaDon> capNhatTrangThai_DaChon(HoaDonDTO hoaDonDTO, long trang_thai_id, String thaoTac, String nguoiThaoTac);
    List<HoaDon> capNhatTrangThaiHuy_DaChon(HoaDonDTO hoaDonDTO, String nguoiThaoTac);

    List<HoaDon> searchAllBill(long trang_thai_id, String search);

    List<HoaDon> searchDateBill(long trang_thai_id, String searchDate);

    ResponseEntity createTimeLine(String thaoTac, long trangThai_id, long hoaDon_id, String nguoiThaoTac);

    ResponseEntity<?> CTChoXacNhan(long id_hoa_don);

    ResponseEntity<?> CTChoGiaoHang(long id_hoa_don);

    ResponseEntity<?> CTDangGiaoHang(long id_hoa_don);

    ResponseEntity<?> CTDaGiaoHang(long id_hoa_don);

    ResponseEntity<?> CTDaHuy(long id_hoa_don);
}