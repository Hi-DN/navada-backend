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

    Page<Product> findProductsByProductNameContains(String productName, Pageable pageable);

    @Query(value = "select h.product.productId from Heart h where h.user=:user")
    List<Long> findHeartProductIdsByUser(@Param("user") User user);

    @Query("select p from Product p where p.productName like %:productName% and p.category.categoryId in (:categoryIds)")
    Page<Product> searchProductsByNameAndCategory(@Param("productName") String productName, @Param("categoryIds") List<Long> categoryIds, Pageable pageable);

    @Query("select p from Product p where p.productName like %:productName% and p.productCost between :lowerBound and :upperBound")
    Page<Product> searchProductsByNameAndCost(@Param("productName") String productName, @Param("lowerBound") int lowerBound, @Param("upperBound") int upperBound, Pageable pageable);

    @Query("select p from Product p where p.productName like %:productName% and p.category.categoryId in (:categoryIds) and p.productCost between :lowerBound and :upperBound")
    Page<Product> searchProductsByNameAndCategoryAndCost(@Param("productName") String productName, @Param("categoryIds") List<Long> categoryIds, @Param("lowerBound") int lowerBound, @Param("upperBound") int upperBound,Pageable pageable);

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
