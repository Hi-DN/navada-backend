package hidn.navada.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final ExchangeJpaRepo exchangeJpaRepo;

    public Exchange testService() {
        Exchange exchange= Exchange.builder()
                .acceptor(1)
                .acceptorProduct(1)
                .requester(2)
                .requesterProduct(2)
                .build();

        return exchangeJpaRepo.save(exchange);
    }
}
