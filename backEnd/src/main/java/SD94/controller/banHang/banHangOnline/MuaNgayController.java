package SD94.controller.banHang.banHangOnline;

import SD94.dto.HoaDonDTO;
import SD94.dto.SanPhamDTO;
import SD94.entity.hoaDon.HoaDon;
import SD94.entity.hoaDon.HoaDonChiTiet;
import SD94.repository.hoaDon.HoaDonChiTietRepository;
import SD94.repository.hoaDon.HoaDonRepository;
import SD94.service.service.MuaNgayService;

import SD94.validator.SanPhamValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/muaNgay")
public class MuaNgayController {
    @Autowired
    MuaNgayService muaNgayService;

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;


    @PostMapping("/check-out")
    public ResponseEntity<?> muaNgayCheckOut(@RequestBody SanPhamDTO dto) {
        ResponseEntity<?> response = SanPhamValidate.checkOut(dto);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else {
            Long id_hoaDon = muaNgayService.muaNgayCheckOut(dto);
            return ResponseEntity.ok(id_hoaDon);
        }
    }


    @GetMapping("/getHoaDon/{id}")
    public ResponseEntity<HoaDon> getHoaDonMuaNgay(@PathVariable("id") long id_HoaDon) {
        HoaDon hoaDon = hoaDonRepository.findByID(id_HoaDon);
        return ResponseEntity.ok(hoaDon);
    }

    @GetMapping("/getHoaDonChiTiet/{id}")
    public List<HoaDonChiTiet> getHoaDonChiTiet(@PathVariable("id") long id_HoaDon) {
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepository.findByIDBill(id_HoaDon);
        return hoaDonChiTiets;
    }

    @GetMapping("/check-out")
    public ResponseEntity<HoaDon> getBill() {
        return muaNgayService.getBill();
    }

    @PostMapping("/add/khuyenMai")
    public HoaDon addDiscount(@RequestBody HoaDonDTO hoaDonDTO) {
        HoaDon hoaDon = muaNgayService.addDiscount(hoaDonDTO);
        return hoaDon;
    }

    @PostMapping("/datHang")
    public ResponseEntity datHang(@RequestBody HoaDonDTO dto) {
        return muaNgayService.datHang(dto);

    }

}