package SD94.controller.banHang.banHangOnline;

import SD94.dto.GioHangDTO;
import SD94.dto.HoaDonDTO;

import SD94.entity.hoaDon.HoaDon;
import SD94.entity.hoaDon.HoaDonChiTiet;

import SD94.service.service.BanHangOnlineService;

import SD94.validator.DatHangValidate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/banHang/online")
public class BanHangOnlineController {

    @Autowired
    BanHangOnlineService banHangOnlineService;

    @PostMapping("/checkOut")
    public ResponseEntity<?> checkout(@RequestBody GioHangDTO dto) {
        Long id_hoaDon = banHangOnlineService.checkout(dto);
        return ResponseEntity.ok(id_hoaDon);
    }

    @GetMapping("/getHoaDon/{id}")
    public ResponseEntity<HoaDon> getHoaDon(@PathVariable("id") long id_HoaDon) {
        return banHangOnlineService.getHoaDon(id_HoaDon);
    }

    @GetMapping("/getHoaDonChiTiet/{id}")
    public List<HoaDonChiTiet> getHoaDonChiTiet(@PathVariable("id") long id_HoaDon) {
        return banHangOnlineService.getHoaDonChiTiet(id_HoaDon);
    }

    @GetMapping("/check-out")
    public ResponseEntity<HoaDon> getBill() {
        return banHangOnlineService.getBill();
    }

    @PostMapping("/add/khuyenMai")
    public ResponseEntity<?> addDiscount(@RequestBody HoaDonDTO hoaDonDTO) {
        return banHangOnlineService.addDiscount(hoaDonDTO);
    }

    @PostMapping("/datHang")
    public ResponseEntity<?> datHang(@RequestBody HoaDonDTO dto) {
        ResponseEntity<?> response = DatHangValidate.datHangcheckout(dto);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else {
            return banHangOnlineService.datHang(dto);
        }
    }
}