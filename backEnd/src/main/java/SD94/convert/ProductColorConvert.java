package SD94.convert;

import SD94.dto.ProductColorDTO;
import SD94.entity.ProductColor;
import org.springframework.stereotype.Component;

@Component
public class ProductColorConvert {

    public ProductColorDTO toDto(ProductColor entity) {
        ProductColorDTO dto = new ProductColorDTO();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setColor(entity.getColor());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedby(entity.getCreatedby());
        dto.setLastModifiedDate(entity.getLastModifiedDate());
        dto.setLastModifiedBy(entity.getLastModifiedBy());
        return dto;
    }

    public ProductColor toEntity(ProductColorDTO dto) {
        ProductColor entity = new ProductColor();
        entity.setId(dto.getId());
        entity.setCode(dto.getCode());
        entity.setColor(dto.getColor());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setCreatedby(dto.getCreatedby());
        entity.setLastModifiedDate(dto.getLastModifiedDate());
        entity.setLastModifiedBy(dto.getLastModifiedBy());
        return entity;
    }
}