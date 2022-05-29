package hidn.navada.exchange.request;

import hidn.navada.product.Product;
import hidn.navada.product.ProductJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeRequestService {
    private final ExchangeRequestJpaRepo exchangeRequestJpaRepo;
    private final ProductJpaRepo productJpaRepo;

    public ExchangeRequest testService() {
        Product product1=productJpaRepo.getById(1L);
        Product product2=productJpaRepo.getById(2L);

        ExchangeRequest exchangeRequest=ExchangeRequest.builder()
                .acceptorProduct(product1)
                .requestProduct(product2)
                .build();

        return exchangeRequestJpaRepo.save(exchangeRequest);
    }
}
