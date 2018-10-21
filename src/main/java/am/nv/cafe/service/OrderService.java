package am.nv.cafe.service;

import am.nv.cafe.dataaccess.model.Order;
import am.nv.cafe.exception.InternalErrorException;
import am.nv.cafe.exception.InvalidObjectException;

public interface OrderService {

    boolean hasOpenOrder(String tableId);

    Order add(String tableId) throws InternalErrorException, InvalidObjectException;

    Order edit(String id, Integer status) throws InvalidObjectException, InternalErrorException;

}
