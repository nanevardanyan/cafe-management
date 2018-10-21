package am.nv.cafe.controller;

import am.nv.cafe.controller.data.Response;
import am.nv.cafe.dataaccess.model.ProductInOrder;
import am.nv.cafe.exception.InternalErrorException;
import am.nv.cafe.exception.InvalidObjectException;
import am.nv.cafe.service.ProductInOrderService;
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
public class ProductInOrderController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductInOrderController.class);

    @Autowired
    private ProductInOrderService productService;

    @PostMapping(path = "/productInOrder/add")
    @ResponseBody
    public Response<ProductInOrder> createProductInOrder(String orderId, String productId, Integer amount) {
        Response<ProductInOrder> response = new Response<>();

        try {
            ProductInOrder addedOrder = productService.add(orderId, productId, amount);
            response.setData(addedOrder).setStatus(HttpStatus.OK);
        } catch (InternalErrorException e) {
            String message = String.format("Failed to add place product in order " +
                    "[ OrderId : %s, ProductId: %s, Amount : %s ]", orderId, productId, amount);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR).setMessage(message);
            LOGGER.error(message);
        } catch (InvalidObjectException e) {
            String message = String.format("Failed to find order/product with the specified id " +
                    "[OrderId: %s, ProductId: %s]", orderId, productId);
            response.setStatus(HttpStatus.BAD_REQUEST).setMessage(message);
            LOGGER.error(message);
        }
        return response;
    }

    @PutMapping(path = "/productInOrder/edit")
    @ResponseBody
    public Response<ProductInOrder> editProductInOrder(@RequestParam("id") String id,
                                                       @RequestParam(required = false) Integer amount,
                                                       @RequestParam(required = false) Integer status) {
        Response<ProductInOrder> response = new Response<>();
        if (amount == null && status == null) {
            response.setStatus(HttpStatus.BAD_REQUEST)
                    .setMessage("Invalid parameters");
            return response;
        }

        try {
            ProductInOrder addedProductInOrder = productService.edit(id, amount, status);
            response.setData(addedProductInOrder).setStatus(HttpStatus.OK);
        } catch (InternalErrorException e) {
            String message = String.format("Failed to add place product in order " +
                    "[ProductInOrder : %s]", id);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR).setMessage(message);
            LOGGER.error(message);
        } catch (InvalidObjectException e) {
            String message = String.format("Failed to find product in order with the specified id " +
                    "[ProductInOrder : %s]", id);
            response.setStatus(HttpStatus.BAD_REQUEST).setMessage(message);
            LOGGER.error(message);
        }
        return response;
    }
}
