package am.nv.cafe.service.impl;

import am.nv.cafe.dataaccess.model.Product;
import am.nv.cafe.exception.InternalErrorException;
import am.nv.cafe.repository.ProductRepository;
import am.nv.cafe.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository repository;

    public Product add(Product product) throws InternalErrorException {
        try {
            return repository.saveAndFlush(product);
        } catch (Exception e) {
            String message = String.format("Failed to add the [ Product: %s]", product);
            LOGGER.error(message, e);
            throw new InternalErrorException(message, e);
        }
    }
}
