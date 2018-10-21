package am.nv.cafe.controller;

import am.nv.cafe.controller.data.Response;
import am.nv.cafe.dataaccess.model.Product;
import am.nv.cafe.exception.InternalErrorException;
import am.nv.cafe.service.impl.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductServiceImpl productService;

    @PostMapping(path = "/product/add")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseBody
    public Response<Product> createProduct(String name, Double price) {
        Response<Product> response = new Response<>();

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);

        try {
            Product productAdded = productService.add(product);
            response.setData(productAdded).setStatus(HttpStatus.OK);
        } catch (InternalErrorException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            LOGGER.error(String.format("Failed to add [ Product : %s ]", product));
        }

        return response;
    }
}
