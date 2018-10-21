package am.nv.cafe.service.impl;

import am.nv.cafe.dataaccess.model.CafeTable;
import am.nv.cafe.dataaccess.model.Order;
import am.nv.cafe.dataaccess.model.lcp.OrderStatus;
import am.nv.cafe.exception.InternalErrorException;
import am.nv.cafe.exception.InvalidObjectException;
import am.nv.cafe.repository.OrderRepository;
import am.nv.cafe.repository.TableRepository;
import am.nv.cafe.service.OrderService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository repository;

    @Autowired
    private TableRepository tableRepository;

    @Override
    public boolean hasOpenOrder(String tableId) {
        List<Order> orders = repository.findByTableId(tableId);
        Optional<Order> any = orders.stream().filter(o -> o.getStatus().equals(OrderStatus.OPEN)).findAny();
        return any.isPresent();
    }

    @Override
    public Order add(String tableId) throws InternalErrorException, InvalidObjectException {
        try {
            Optional<CafeTable> table = tableRepository.findById(tableId);

            if (table.isPresent()) {
                Order order = new Order();
                order.setDate(LocalDateTime.now());
                order.setStatus(OrderStatus.OPEN);
                order.setTable(table.get());

                repository.saveAndFlush(order);
                return order;
            } else {
                String message = String.format("Failed to find table with the specified id " +
                        "[ TableId : %s]", tableId);
                LOGGER.error(message);
                throw new InvalidObjectException(message);
            }
        } catch (RuntimeException e) {
            String message = String.format("Failed to place the order " +
                    "[TableId: %s]", tableId);
            LOGGER.error(message, e);
            throw new InternalErrorException(message);
        }
    }

    @Override
    public Order edit(String id, Integer status) throws InvalidObjectException, InternalErrorException {
        try {
            Optional<Order> order = repository.findById(id);
            if (order.isPresent()) {
                Order value = order.get();
                if (status != null) {
                    value.setStatus(OrderStatus.valueOf(status));
                }
                return repository.saveAndFlush(value);
            } else {
                String message = String.format("Failed to find order with the specified id " +
                        "[OrderId: %s]", id);
                LOGGER.error(message);
                throw new InvalidObjectException(message);
            }
        } catch (RuntimeException e) {
            String message = String.format("Failed to edit order [OrderId: %s]", id);
            LOGGER.error(message, e);
            throw new InternalErrorException(message, e);
        }
    }
}
