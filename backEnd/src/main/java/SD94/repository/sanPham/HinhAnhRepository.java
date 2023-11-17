package SD94.repository.sanPham;

import SD94.entity.sanPham.HinhAnh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HinhAnhRepository extends JpaRepository<HinhAnh, Long> {

    @Query(value = "select * from hinh_anh ha join san_pham_chi_tiet spct on ha.id_product = spct.id\n" +
            "         join san_pham sp on spct.san_pham_id = sp.id\n" +
            "         where anh_mac_dinh = true and sp.id =:idsp and ha.is_deleted = false limit 1", nativeQuery = true)
    HinhAnh getFirstAnhByIdSanPham(@Param("idsp")Long idsp);

    @Query(value = "SELECT * FROM hinh_anh WHERE is_deleted = false ORDER BY id DESC", nativeQuery = true)
    List<HinhAnh> findAll();

    @Query(value = "select * from hinh_anh where id = ? and is_deleted = false", nativeQuery = true)
    HinhAnh findByID(Long id);

    @Query(value = "SELECT * FROM hinh_anh WHERE is_deleted = false AND DATE(started_date) = ?", nativeQuery = true)
    List<HinhAnh> findByDate(LocalDate ngayTao);

    @Query(value = "select * from hinh_anh where name = ?", nativeQuery = true)
    Optional<HinhAnh> findByName(String name);

    @Query(value = "select * from hinh_anh where id_product = ? and is_deleted = false", nativeQuery = true)
    List<HinhAnh> findByIDProduct(long id_sanPham);

    @Query(value = "select * from hinh_anh where id_product = ? and anh_mac_dinh = true;", nativeQuery = true)
    HinhAnh findAnhMacDinh(long id_sp);

    @Modifying
    @Query(value = "delete from hinh_anh where id = ?", nativeQuery = true)
    void xoaAnh(long id);

    @Query(value = "select name from hinh_anh where id_product = ? and anh_mac_dinh = true", nativeQuery = true)
    String getTenAnhSanPham_HienThiDanhSach(long sanPham_id);

    @Query(value = "select name from hinh_anh where id_product = ?1 and id_color = ?2 LIMIT 1", nativeQuery = true)
    String getAnhSPByMauSacAndSPID(long sanPham_id, long mau_sac_id);

    @Query(value = "select * from hinh_anh where id_product = ?", nativeQuery = true)
    List<HinhAnh> getHinhAnhByProductID(long sanPham_id);


}
