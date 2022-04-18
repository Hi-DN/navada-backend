package hidn.navada.exchange;

import hidn.navada.product.Product;
import hidn.navada.product.ProductJpaRepo;
import hidn.navada.user.User;
import hidn.navada.user.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final ExchangeJpaRepo exchangeJpaRepo;
    private final ProductJpaRepo productJpaRepo;
    private final UserJpaRepo userJpaRepo;

    public Exchange testService() {
        User acceptor=userJpaRepo.getById(1L);
        Product acceptorProduct=productJpaRepo.getById(1L);
        User requester=userJpaRepo.getById(2L);
        Product requesterProduct=productJpaRepo.getById(2L);

        Exchange exchange= Exchange.builder()
                .acceptor(acceptor)
                .acceptorProduct(acceptorProduct)
                .requester(requester)
                .requesterProduct(requesterProduct)
                .build();

        return exchangeJpaRepo.save(exchange);
    }
}
