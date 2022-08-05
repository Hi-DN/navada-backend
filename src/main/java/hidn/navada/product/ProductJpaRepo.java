package hidn.navada.product;

import hidn.navada.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductJpaRepo extends JpaRepository<Product,Long> {
    Page<Product> findProductsByUser(User user, Pageable pageable);

    @Query(value = "select r.requesterProduct from Request r where r.requester=:user and r.acceptorProduct=:acceptorProduct and r.exchangeStatusCd in (0,1)")
    List<Product> findProductsByUserAlreadyRequestedToTheirProduct(@Param("user")User user, @Param("acceptorProduct")Product acceptorProduct);
}
