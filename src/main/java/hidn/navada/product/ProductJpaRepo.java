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

    @Query(value = "select p from Product p where (:#{#options.productName} is null or p.productName like %:#{#options.productName}%) " +
            "and (:#{#options.categoryIds} is null or p.category.categoryId in (:#{#options.categoryIds})) " +
            "and (:#{#options.lowerCostBound} is null or (:#{#options.lowerCostBound} <= p.productCost)) " +
            "and (:#{#options. upperCostBound} is null or (p.productCost <= :#{#options.upperCostBound})) ")
    Page<Product> findProductsByOptions(@Param("options") ProductSearchOptions options, Pageable pageable);


    @Query(value = "select h.product.productId from Heart h where h.user=:user")
    List<Long> findHeartProductIdsByUser(@Param("user") User user);

    List<Product> findProductsByUserAndProductExchangeStatusCd(User user, char productExchangeStatusCd);

    @Query(value = "select requester_product_id from request where requester_id=:userId and acceptor_product_id=:acceptorProductId and exchange_status_cd=0", nativeQuery = true)
    List<Long> findProductsByUserAlreadyRequestedToTheirProduct(@Param("userId")Long userId, @Param("acceptorProductId")Long acceptorProductId);

    @Query(value = "select p from Product p " +
            "where p.user=:user " +
            "and p.productExchangeStatusCd='0'" +
            "and p not in " +
            "(select r.acceptorProduct from Request r where r.requesterProduct=:wantedProduct and r.acceptor=:user)" +
            "and p not in " +
            "(select r.requesterProduct from Request r where r.acceptorProduct=:wantedProduct and r.requester=:user)")
    Page<Product> findProductsForRequest(@Param("user") User user, @Param("wantedProduct") Product wantedProduct, Pageable pageable);
}
