package am.nv.cafe.service;

import am.nv.cafe.dataaccess.model.ProductInOrder;
import am.nv.cafe.exception.InternalErrorException;
import am.nv.cafe.exception.InvalidObjectException;

public interface ProductInOrderService {

    ProductInOrder add(String orderId, String productId, Integer amount) throws InvalidObjectException, InternalErrorException;

    ProductInOrder edit(String id, Integer amount, Integer status) throws InvalidObjectException, InternalErrorException;

}
