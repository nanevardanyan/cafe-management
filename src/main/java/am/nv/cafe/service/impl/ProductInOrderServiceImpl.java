package am.nv.cafe.service.impl;

import am.nv.cafe.dataaccess.model.Order;
import am.nv.cafe.dataaccess.model.Product;
import am.nv.cafe.dataaccess.model.ProductInOrder;
import am.nv.cafe.dataaccess.model.lcp.ProductInOrderStatus;
import am.nv.cafe.exception.InternalErrorException;
import am.nv.cafe.exception.InvalidObjectException;
import am.nv.cafe.repository.OrderRepository;
import am.nv.cafe.repository.ProductInOrderRepository;
import am.nv.cafe.repository.ProductRepository;
import am.nv.cafe.service.ProductInOrderService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductInOrderServiceImpl implements ProductInOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductInOrderServiceImpl.class);

    @Autowired
    private ProductInOrderRepository repository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductInOrder add(String orderId, String productId, Integer amount) throws InvalidObjectException, InternalErrorException {
        try {
            Optional<Order> order = orderRepository.findById(orderId);
            Optional<Product> product = productRepository.findById(productId);

            if (order.isPresent() && product.isPresent()) {
                ProductInOrder productInOrder = new ProductInOrder();
                productInOrder.setAmount(amount);
                productInOrder.setOrder(order.get());
                productInOrder.setProduct(product.get());
                productInOrder.setStatus(ProductInOrderStatus.ACTIVE);

                return repository.saveAndFlush(productInOrder);
            } else {
                String message = String.format("Failed to find order/product with the specified id " +
                        "[OrderId: %s, ProductId: %s]", orderId, productId);
                LOGGER.error(message);
                throw new InvalidObjectException(message);
            }
        } catch (RuntimeException e) {
            String message = String.format("Failed to add place product in order " +
                    "[ OrderId : %s, ProductId: %s, Amount : %s ]", orderId, productId, amount);
            LOGGER.error(message, e);
            throw new InternalErrorException(message, e);
        }
    }

    @Override
    public ProductInOrder edit(String id, Integer amount, Integer status) throws InvalidObjectException, InternalErrorException {
        try {
            Optional<ProductInOrder> productInOrder = repository.findById(id);
            if (productInOrder.isPresent()) {
                ProductInOrder value = productInOrder.get();
                if (amount != null) {
                    value.setAmount(amount);
                }
                if (status != null) {
                    value.setStatus(ProductInOrderStatus.valueOf(status));
                }
                return repository.saveAndFlush(value);
            } else {
                String message = String.format("Failed to find product in order with the specified id " +
                        "[ProductInOrderId: %s]", id);
                LOGGER.error(message);
                throw new InvalidObjectException(message);
            }
        } catch (RuntimeException e) {
            String message = String.format("Failed to edit product in order " +
                    "[ ProductInOrder: %s]", id);
            LOGGER.error(message, e);
            throw new InternalErrorException(message, e);
        }
    }
}
