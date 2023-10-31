package SD94.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductSizeDTO {

    private long id;

    private String shoeSize;

    private Date createdDate;

    private String createdby;

    private Date lastModifiedDate;

    private String lastModifiedBy;

    private boolean isDeleted;
}
