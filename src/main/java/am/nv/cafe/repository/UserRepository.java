package am.nv.cafe.repository;

import am.nv.cafe.dataaccess.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Retrieves true in case waiter exists with the specified id, otherwise false
     */
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM User e WHERE id = :id")
    boolean exists(@Param("id") String id);

    User findByEmail(String email);

}
