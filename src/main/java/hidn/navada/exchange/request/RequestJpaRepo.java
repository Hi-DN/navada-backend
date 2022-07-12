package hidn.navada.exchange.request;

import hidn.navada.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestJpaRepo extends JpaRepository<Request,Long> {
    List<Request> findByRequesterUserId(Long userId);
    List<Request> findByAcceptorProductAndExchangeStatusCd(Product product, int exchangeStatusCd);
}
