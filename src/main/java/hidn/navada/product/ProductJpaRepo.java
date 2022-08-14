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
    Page<Product> findProductsByProductNameContains(String productName, Pageable pageable);

    @Query(value = "select h.product.productId from Heart h where h.user=:user")
    List<Long> findHeartProductIdsByUser(@Param("user") User user);

    @Query("select p from Product p where p.productName like %:productName% and p.category.categoryId in (:categoryIds)")
    Page<Product> searchProductsByNameAndCategory(@Param("productName") String productName, @Param("categoryIds") List<Long> categoryIds, Pageable pageable);

    @Query("select p from Product p where p.productName like %:productName% and p.productCost between :lowerBound and :upperBound")
    Page<Product> searchProductsByNameAndCost(@Param("productName") String productName, @Param("lowerBound") int lowerBound, @Param("upperBound") int upperBound, Pageable pageable);

    @Query("select p from Product p where p.productName like %:productName% and p.category.categoryId in (:categoryIds) and p.productCost between :lowerBound and :upperBound")
    Page<Product> searchProductsByNameAndCategoryAndCost(@Param("productName") String productName, @Param("categoryIds") List<Long> categoryIds, @Param("lowerBound") int lowerBound, @Param("upperBound") int upperBound,Pageable pageable);
}
