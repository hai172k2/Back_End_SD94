
package SD94.repository.hoaDon;

import SD94.entity.hoaDon.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {
    @Query(value = "SELECT * FROM hoa_don WHERE is_deleted = false and trang_thai_id = ?  ORDER BY id DESC", nativeQuery = true)
    List<HoaDon> findHoaDonByTrangThai(long trang_thai_id);

    @Query(value = "SELECT * from hoa_don  WHERE is_deleted = false and  trang_thai_id = ?1 " +
            "AND (ma_hoa_don LIKE %?1% OR SDTNguoi_nhan LIKE %?1% OR email_nguoi_nhan LIKE %?1%)", nativeQuery = true)
    List<HoaDon> findBill(long trang_thai_id, String input);

    @Query(value = "SELECT * from hoa_don WHERE is_deleted = false and trang_thai_id = ? AND DATE(created_date) = ?", nativeQuery = true)
    List<HoaDon> findBillByDate(long trang_thai_id, LocalDate ngayTao);

    @Query(value = "select * from hoa_don where id = ? and is_deleted = false", nativeQuery = true)
    HoaDon findByID(Long id);

}


