package hidn.navada.exchange.request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRequestJpaRepo extends JpaRepository<ExchangeRequest,Long> {
    List<ExchangeRequest> findByRequesterUserId(Long userId);
}
