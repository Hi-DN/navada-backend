package hidn.navada.product;

import hidn.navada.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductJpaRepo extends JpaRepository<Product, Long> {
    Page<Product> findProductsByUser(User user, Pageable pageable);

    List<Product> findProductsByUserAndProductStatusCd(User user, int productStatusCd);

    @Query(value = "select requester_product_id from request where requester_id=:userId and acceptor_product_id=:acceptorProductId and exchange_status_cd=0", nativeQuery = true)
    List<Long> findProductsByUserAlreadyRequestedToTheirProduct(@Param("userId")Long userId, @Param("acceptorProductId")Long acceptorProductId);
}
