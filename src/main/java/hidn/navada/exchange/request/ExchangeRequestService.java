package hidn.navada.exchange.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRequestService {
    private final ExchangeRequestJpaRepo exchangeRequestJpaRepo;

    public ExchangeRequest testService() {
        ExchangeRequest exchangeRequest=ExchangeRequest.builder()
                .exchangeProduct(1)
                .requestProduct(2)
                .build();

        return exchangeRequestJpaRepo.save(exchangeRequest);
    }
}
