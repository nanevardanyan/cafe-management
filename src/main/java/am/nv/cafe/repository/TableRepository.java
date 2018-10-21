package am.nv.cafe.repository;

import am.nv.cafe.dataaccess.model.CafeTable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<CafeTable, String> {

    /**
     * Retrieves true in case table exists with the specified id, otherwise false
     */
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM CafeTable e WHERE id = :id")
    boolean exists(@Param("id") String id);

    List<CafeTable> findAllByWaiterId(String waiterId);
}
