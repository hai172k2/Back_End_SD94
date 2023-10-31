package SD94.entity.product;

import SD94.entity.customer.Base;
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
@Table(name = "productDetails")
public class ProductDetails extends Base implements Serializable {

    @Column(name = "quantity", columnDefinition = "int null")
    private Integer quantity;

    @Column(name = "status", columnDefinition = "int null")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "idProduct", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "idColor", referencedColumnName = "id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "idSize", referencedColumnName = "id")
    private Size size;

}