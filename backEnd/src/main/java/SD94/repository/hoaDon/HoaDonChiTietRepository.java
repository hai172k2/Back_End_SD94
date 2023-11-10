package SD94.repository.hoaDon;


import SD94.entity.hoaDon.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Long> {
    @Query(value = "SELECT * FROM hoa_don_chi_tiet WHERE is_deleted = false  ORDER BY id DESC", nativeQuery = true)
    List<HoaDonChiTiet> findAllDetailedInvoice();

    @Query(value = "select * from hoa_don_chi_tiet where hoa_don_id = ? and is_deleted = false", nativeQuery = true)
    List<HoaDonChiTiet> findByIDBill(Long id);

    @Query(value = "select * from hoa_don_chi_tiet where hoa_don_id = ? and san_pham_chi_tiet_id=? and is_deleted = false", nativeQuery = true)
    Optional<HoaDonChiTiet> checkHDCT(long hoa_don_id, long san_pham_chi_tiet_id);
}
