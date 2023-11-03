package SD94.dto;

import SD94.entity.sanPham.MauSac;
import SD94.entity.sanPham.SanPhamChiTiet;
import lombok.Data;

@Data
public class HinhAnhDTO {

    private long id;

    private String img;

    private String name;

    private MauSac mauSac;

    private SanPhamChiTiet sanPhamChiTiet;

    private long id_SPCT;

}
