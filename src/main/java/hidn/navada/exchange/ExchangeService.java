package hidn.navada.exchange;

import hidn.navada.exchange.request.ExchangeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final ExchangeJpaRepo exchangeJpaRepo;

    public Exchange createExchange(ExchangeRequest exchangeRequest) {
        Exchange exchange = new Exchange();

        exchange.setAcceptor(exchangeRequest.getAcceptor());
        exchange.setAcceptorProduct(exchangeRequest.getAcceptorProduct());
        exchange.setRequester(exchangeRequest.getRequester());
        exchange.setRequesterProduct(exchangeRequest.getRequesterProduct());

        exchangeJpaRepo.save(exchange);

        return exchange;
    }
}
