package hidn.navada.product;

import hidn.navada.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductJpaRepo extends JpaRepository<Product, Long> {
    @Query(value = "select p from Product p where p.user=:user" +
            " and (coalesce(:productExchangeStatusCds,null) is null or (p.productExchangeStatusCd in :productExchangeStatusCds))")
    Page<Product> findProductsByUser(@Param("user") User user, @Param("productExchangeStatusCds") List<Character> productExchangeStatusCds, Pageable pageable);

    @Query(value = "select * from Product p where (:#{#options.productName} is null or p.product_name like :#{#options.productName}) " +
            "and (coalesce(:#{#options.categoryIds},null) is null or p.category_id in (:#{#options.categoryIds})) " +
            "and (:#{#options.lowerCostBound} is null or (:#{#options.lowerCostBound} <= p.product_cost)) " +
            "and (:#{#options. upperCostBound} is null or (p.product_cost <= :#{#options.upperCostBound})) "+
            "and (coalesce(:#{#options.productExchangeStatusCds},null) is null or p.product_exchange_status_cd in (:#{#options.productExchangeStatusCds}))",nativeQuery = true)
    Page<Product> findProductsByOptions(@Param("options") ProductSearchOptions options, Pageable pageable);

    @Query(value = "select * from Product p where (:#{#options.productName} is null or p.product_name like :#{#options.productName}) " +
            "and (coalesce(:#{#options.categoryIds},null) is null or p.category_id in (:#{#options.categoryIds})) " +
            "and (:#{#options.lowerCostBound} is null or (:#{#options.lowerCostBound} <= p.product_cost)) " +
            "and (:#{#options. upperCostBound} is null or (p.product_cost <= :#{#options.upperCostBound})) "+
            "and (coalesce(:#{#options.productExchangeStatusCds},null) is null or p.product_exchange_status_cd in(:#{#options.productExchangeStatusCds})) "+
            "and p.user_id != :userId",nativeQuery = true)
    Page<Product> findProductsByOptionsExceptMyProduct(@Param("options") ProductSearchOptions options, @Param("userId") long userId,Pageable pageable);


    @Query(value = "select h.product.productId from Heart h where h.user=:user")
    List<Long> findHeartProductIdsByUser(@Param("user") User user);

    List<Product> findProductsByUserUserId(@Param("userId") Long userId);

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
