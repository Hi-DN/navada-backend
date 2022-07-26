package hidn.navada.product;

import hidn.navada.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepo extends JpaRepository<Product,Long> {
    Page<Product> findProductsByUser(User user, Pageable pageable);
}
