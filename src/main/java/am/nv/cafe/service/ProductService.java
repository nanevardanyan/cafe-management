package am.nv.cafe.service;

import am.nv.cafe.dataaccess.model.Product;
import am.nv.cafe.exception.InternalErrorException;

public interface ProductService {

    Product add(Product product) throws InternalErrorException;

}
