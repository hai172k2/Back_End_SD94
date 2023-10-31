package SD94.entity.hoaDon;

import SD94.entity.Base;
import SD94.entity.nhanVien.NhanVien;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "LichSuHoaDon")
public class LichSuHoaDon extends Base implements Serializable {

    @Column(name = "thaoTac", columnDefinition = "nvarchar(256) not null unique")
    private String thaoTac;

    @ManyToOne
    @JoinColumn(name = "trangThai_id", referencedColumnName = "id")
    private TrangThai trangThai;

    @ManyToOne
    @JoinColumn(name = "nhanVien_id", referencedColumnName = "id")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "hoaDon_id", referencedColumnName = "id")
    private HoaDon hoaDon;

}
