package hidn.navada.exchange.request;

import hidn.navada.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRequestJpaRepo extends JpaRepository<ExchangeRequest,Long> {
    List<ExchangeRequest> findByRequesterUserId(Long userId);
    List<ExchangeRequest> findByRequesterProductAndExchangeStatusCd(Product product, int exchangeStatusCd);
}
