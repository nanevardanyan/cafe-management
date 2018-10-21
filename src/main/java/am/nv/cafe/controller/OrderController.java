package am.nv.cafe.controller;

import am.nv.cafe.controller.data.Response;
import am.nv.cafe.dataaccess.model.Order;
import am.nv.cafe.exception.InternalErrorException;
import am.nv.cafe.exception.InvalidObjectException;
import am.nv.cafe.service.impl.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping(path = "/order/add")
    @ResponseBody
    public Response<Order> createOrder(String tableId) {
        Response<Order> response = new Response<>();

        if (orderService.hasOpenOrder(tableId)) {
            String message = getMessage("msg.table.opened.order", tableId);
            response.setMessage(message).setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            LOGGER.error(message);
            return response;
        }

        try {
            Order addedOrder = orderService.add(tableId);
            response.setData(addedOrder).setStatus(HttpStatus.OK);
        } catch (InternalErrorException e) {
            String message = getMessage("msg.order.failed", tableId);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR).setMessage(message);
            LOGGER.error(message);
        } catch (InvalidObjectException e) {
            String message = getMessage("msg.table.failed.to.find", tableId);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR).setMessage(message);
            LOGGER.error(message);
        }

        return response;
    }

    @PutMapping(path = "/order/edit")
    @ResponseBody
    public Response<Order> editOrder(@RequestParam("id") String id,
                                     @RequestParam(required = false) Integer status) {
        Response<Order> response = new Response<>();

        try {
            Order order = orderService.edit(id, status);
            response.setData(order).setStatus(HttpStatus.OK);
        } catch (InternalErrorException e) {
            String message = getMessage("msg.order.failed.to.edit", id);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR).setMessage(message);
            LOGGER.error(message);
        } catch (InvalidObjectException e) {
            String message = getMessage("msg.order.failed.to.find", id);
            response.setStatus(HttpStatus.BAD_REQUEST).setMessage(message);
            LOGGER.error(message);
        }
        return response;
    }

}
