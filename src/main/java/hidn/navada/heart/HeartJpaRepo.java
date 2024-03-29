package hidn.navada.heart;

import hidn.navada.product.Product;
import hidn.navada.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HeartJpaRepo extends JpaRepository<Heart,Long> {

    @Query(value = "select h from Heart h join fetch h.product where h.user=:user",
    countQuery = "select count(h) from Heart h inner join h.product where h.user=:user")
    Page<Heart> findHeartsByUser(@Param("user") User user, Pageable pageable);

    Heart findByProductAndUser(Product product, User user);

    @Query(value = "select h from Heart h join fetch h.product p where h.user=:user and p.productExchangeStatusCd='0'",
    countQuery = "select count(h) from Heart h inner join h.product p where h.user=:user and p.productExchangeStatusCd='0'")
    Page<Heart> findHeartsByUserAndProductExchangeStatusCd(@Param("user") User user, Pageable pageable);

    Boolean existsByUserAndProduct(User user, Product product);
}
