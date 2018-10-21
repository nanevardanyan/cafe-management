package am.nv.cafe.dataaccess.converter;

import am.nv.cafe.dataaccess.model.lcp.OrderStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OrderStatus status) {
        return status.getValue();
    }

    @Override
    public OrderStatus convertToEntityAttribute(Integer value) {
        return OrderStatus.valueOf(value);
    }
}
