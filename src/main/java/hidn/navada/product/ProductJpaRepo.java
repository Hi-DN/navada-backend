package hidn.navada.product;

import hidn.navada.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepo extends JpaRepository<Product,Long> {
    List<Product> findProductsByUser(User user);
}
