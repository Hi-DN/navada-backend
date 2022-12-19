package hidn.navada.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User,Long> {
    Optional<User> findByUserEmailAndUserNickname(@Param("userEmail") String userEmail, @Param("userNickName") String userNickName);

    @Query(value = "SELECT * FROM `user` u WHERE u.user_id = ( SELECT p.user_id FROM product p WHERE p.product_id=:productId )", nativeQuery = true)
    User findUserByProduct(@Param("productId") long productId);
}
