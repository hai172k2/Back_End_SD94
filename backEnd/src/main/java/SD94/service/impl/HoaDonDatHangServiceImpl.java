package SD94.service.impl;


import SD94.dto.HoaDonDTO;
import SD94.entity.hoaDon.HoaDon;
import SD94.entity.hoaDon.HoaDonChiTiet;
import SD94.entity.hoaDon.LichSuHoaDon;
import SD94.entity.hoaDon.TrangThai;
import SD94.entity.nhanVien.NhanVien;
import SD94.repository.hoaDon.HoaDonChiTietRepository;
import SD94.repository.hoaDon.HoaDonRepository;
import SD94.repository.hoaDon.LichSuHoaDonRepository;
import SD94.repository.hoaDon.TrangThaiRepository;
import SD94.repository.nhanVien.NhanVienRepository;
import SD94.service.service.HoaDonDatHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import java.util.*;

@Service
public class HoaDonDatHangServiceImpl implements HoaDonDatHangService {
    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    TrangThaiRepository trangThaiRepository;

    @Autowired
    LichSuHoaDonRepository lichSuHoaDonRepository;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Override
    public List<HoaDon> findHoaDonByTrangThai(long trang_thai_id) {
        List<HoaDon> hoaDonList = hoaDonRepository.findHoaDonByTrangThai(trang_thai_id);
        return hoaDonList;
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> capNhatTrangThai(long trang_thai_id, long id_bill) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_bill);
        Optional<TrangThai> optionalTrangThai = trangThaiRepository.findById(trang_thai_id);
        if (optionalTrangThai.isPresent()) {
            TrangThai trangThai = optionalTrangThai.get();
            hoaDon.setTrangThai(trangThai);
            hoaDonRepository.save(hoaDon);
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> capNhatTrangThai_TatCa(long trang_thai_id, long trang_thai_id_sau, String thaoTac, String nguoiThaoTac) {
        List<HoaDon> list = hoaDonRepository.findHoaDonByTrangThai(trang_thai_id);
        for (HoaDon hoaDon : list) {
            Optional<TrangThai> optionalTrangThai = trangThaiRepository.findById(trang_thai_id_sau);
            if (optionalTrangThai.isPresent()) {
                TrangThai trangThai = optionalTrangThai.get();
                hoaDon.setTrangThai(trangThai);
                createTimeLine(thaoTac, trang_thai_id_sau, hoaDon.getId(), nguoiThaoTac);
                hoaDonRepository.save(hoaDon);
            }
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public List<HoaDon> capNhatTrangThai_DaChon(HoaDonDTO hoaDonDTO, long trang_thai_id, String thaoTac, String nguoiThaoTac) {
        for (Long id_hoaDon : hoaDonDTO.getId_hoaDon()) {
            HoaDon hoaDon = hoaDonRepository.findByID(id_hoaDon);
            Optional<TrangThai> optionalTrangThai = trangThaiRepository.findById(trang_thai_id);
            if (optionalTrangThai.isPresent()) {
                TrangThai trangThai = optionalTrangThai.get();
                hoaDon.setTrangThai(trangThai);
                createTimeLine(thaoTac, trang_thai_id, id_hoaDon, nguoiThaoTac);
                hoaDonRepository.save(hoaDon);
            }
        }
        List<HoaDon> hoaDonList = hoaDonRepository.findHoaDonByTrangThai(trang_thai_id-1);
        return hoaDonList;
    }

    @Override
    public List<HoaDon> capNhatTrangThaiHuy_DaChon(HoaDonDTO hoaDonDTO, String nguoiThaoTac) {
        for (Long id_hoaDon : hoaDonDTO.getId_hoaDon()) {
            HoaDon hoaDon = hoaDonRepository.findByID(id_hoaDon);
            Optional<TrangThai> optionalTrangThai = trangThaiRepository.findById(5L);
            if (optionalTrangThai.isPresent()) {
                TrangThai trangThai = optionalTrangThai.get();
                hoaDon.setTrangThai(trangThai);
                createTimeLine("Huỷ đơn", 5L, id_hoaDon, nguoiThaoTac);
                hoaDonRepository.save(hoaDon);
            }
        }
        List<HoaDon> hoaDonList = hoaDonRepository.findHoaDonByTrangThai(1L);
        return hoaDonList;
    }


    @Override
    public List<HoaDon> searchAllBill(long trang_thai_id, String search) {
        List<HoaDon> hoaDonList = hoaDonRepository.findBill(trang_thai_id, search);
        return hoaDonList;
    }

    @Override
    public List<HoaDon> searchDateBill(long trang_thai_id, String searchDate) {
        LocalDate search = LocalDate.parse(searchDate);
        List<HoaDon> hoaDonList = hoaDonRepository.findBillByDate(trang_thai_id, search);
        return hoaDonList;
    }

    @Override
    public ResponseEntity createTimeLine(String thaoTac, long trangThai_id, long hoaDon_id, String nguoiThaoTac) {
        HoaDon hoaDon = hoaDonRepository.findByID(hoaDon_id);
        TrangThai trangThai = trangThaiRepository.findByID(trangThai_id);

        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setThaoTac(thaoTac);
        lichSuHoaDon.setHoaDon(hoaDon);
        lichSuHoaDon.setTrangThai(trangThai);
        lichSuHoaDon.setCreatedDate(new Date());
        lichSuHoaDon.setNguoiThaoTac(nguoiThaoTac);
        lichSuHoaDonRepository.save(lichSuHoaDon);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> CTChoXacNhan(long id_hoa_don) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_hoa_don);
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
        LichSuHoaDon timeLine_ChoXacNhan = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 1L);
        Map<String, Object> response = new HashMap<>();
        response.put("list_HDCT", hoaDonChiTiets);
        response.put("hoaDon", hoaDon);
        response.put("timeLine", timeLine_ChoXacNhan);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> CTChoGiaoHang(long id_hoa_don) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_hoa_don);
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
        LichSuHoaDon timeLine_ChoXacNhan = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 1L);
        LichSuHoaDon timeLine_ChoGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 2L);
        Map<String, Object> response = new HashMap<>();
        response.put("list_HDCT", hoaDonChiTiets);
        response.put("hoaDon", hoaDon);
        response.put("timeLine_ChoXacNhan", timeLine_ChoXacNhan);
        response.put("timeLine_ChoGiaoHang", timeLine_ChoGiaoHang);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> CTDangGiaoHang(long id_hoa_don) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_hoa_don);
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
        LichSuHoaDon timeLine_ChoXacNhan = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 1L);
        LichSuHoaDon timeLine_ChoGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 2L);
        LichSuHoaDon timeLine_DangGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 3L);
        Map<String, Object> response = new HashMap<>();
        response.put("list_HDCT", hoaDonChiTiets);
        response.put("hoaDon", hoaDon);
        response.put("timeLine_ChoXacNhan", timeLine_ChoXacNhan);
        response.put("timeLine_ChoGiaoHang", timeLine_ChoGiaoHang);
        response.put("timeLine_DangGiaoHang", timeLine_DangGiaoHang);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> CTDaGiaoHang(long id_hoa_don) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_hoa_don);
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
        LichSuHoaDon timeLine_ChoXacNhan = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 1L);
        LichSuHoaDon timeLine_ChoGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 2L);
        LichSuHoaDon timeLine_DangGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 3L);
        LichSuHoaDon timeLine_DaGiaoHang = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 4L);
        Map<String, Object> response = new HashMap<>();
        response.put("list_HDCT", hoaDonChiTiets);
        response.put("hoaDon", hoaDon);
        response.put("timeLine_ChoXacNhan", timeLine_ChoXacNhan);
        response.put("timeLine_ChoGiaoHang", timeLine_ChoGiaoHang);
        response.put("timeLine_DangGiaoHang", timeLine_DangGiaoHang);
        response.put("timeLine_DaGiaoHang", timeLine_DaGiaoHang);
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> CTDaHuy(long id_hoa_don) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_hoa_don);
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(hoaDon.getId());
        LichSuHoaDon timeLine_ChoXacNhan = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 1L);
        LichSuHoaDon timeLine_DaHuy = lichSuHoaDonRepository.getTimeLine(hoaDon.getId(), 5L);
        Map<String, Object> response = new HashMap<>();
        response.put("list_HDCT", hoaDonChiTiets);
        response.put("hoaDon", hoaDon);
        response.put("timeLine_ChoXacNhan", timeLine_ChoXacNhan);
        response.put("timeLine_DaHuy", timeLine_DaHuy);
        return ResponseEntity.ok().body(response);
    }
}