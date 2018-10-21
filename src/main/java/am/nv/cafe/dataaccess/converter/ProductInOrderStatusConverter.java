package am.nv.cafe.dataaccess.converter;

import am.nv.cafe.dataaccess.model.lcp.ProductInOrderStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProductInOrderStatusConverter implements AttributeConverter<ProductInOrderStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ProductInOrderStatus status) {
        return status.getValue();
    }

    @Override
    public ProductInOrderStatus convertToEntityAttribute(Integer integer) {
        return ProductInOrderStatus.valueOf(integer);
    }
}
