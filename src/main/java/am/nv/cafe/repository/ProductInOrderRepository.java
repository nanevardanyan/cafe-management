package am.nv.cafe.repository;

import am.nv.cafe.dataaccess.model.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, String> {

}
