package hidn.navada.heart;

import hidn.navada.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HeartJpaRepo extends JpaRepository<Heart,Long> {
    Page<Heart> findHeartsByUser(User user, Pageable pageable);

    @Query(value = "select h from Heart h where h.user=:user and h.product.productStatusCd=0")
    Page<Heart> findHeartsByUserAndProductStatusCd(@Param("user") User user, Pageable pageable);

    @Query(value = "select h.product.productId from Heart h where h.user=:user")
    List<Long> findLikeProductIdsByUser(@Param("user") User user);
}
