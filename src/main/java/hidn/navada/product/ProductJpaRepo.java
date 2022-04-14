package hidn.navada.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepo extends JpaRepository<Product,Long> {
}
