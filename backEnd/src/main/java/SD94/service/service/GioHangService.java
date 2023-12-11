package SD94.service.service;

import SD94.dto.GioHangChiTietDTO;
import SD94.entity.gioHang.GioHang;
import SD94.entity.gioHang.GioHangChiTiet;
import SD94.entity.khachHang.KhachHang;
import SD94.entity.sanPham.SanPhamChiTiet;
import SD94.repository.gioHang.GioHangChiTietRepository;
import SD94.repository.gioHang.GioHangRepository;
import SD94.repository.khachHang.KhachHangRepository;
import SD94.repository.sanPham.SanPhamChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GioHangService {

    @Autowired
    GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    SanPhamChiTietRepository sanPhamChiTietRepositoryl;

    @Autowired
    GioHangRepository gioHangRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    public ResponseEntity<?> updateSoLuong(GioHangChiTietDTO dto) {
        Map<String, String> respone = new HashMap<>();

        GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findById(dto.getId()).get();
        SanPhamChiTiet sanPhamChiTiet = dto.getSanPhamChiTiet();

        int soLuongSanPhamHienCo = sanPhamChiTiet.getSoLuong();
        int soLuongUpdate = dto.getSoLuongCapNhat();

        if (soLuongSanPhamHienCo < soLuongUpdate) {
            respone.put("err", "Số lượng nhập vào không được vượt quá " + soLuongSanPhamHienCo);
            return ResponseEntity.badRequest().body(respone);
        } else {
            gioHangChiTiet.setSoLuong(soLuongUpdate);
            gioHangChiTiet.setThanhTien(BigDecimal.valueOf(soLuongUpdate * sanPhamChiTiet.getSanPham().getGia()));
            gioHangChiTietRepository.save(gioHangChiTiet);

            respone.put("success", "Cập nhật số lượng thành công");
            return ResponseEntity.ok().body(respone);
        }
    }
}