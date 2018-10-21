package am.nv.cafe.repository;

import am.nv.cafe.dataaccess.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByTableId(@Param("tableId") String tableId);
}
