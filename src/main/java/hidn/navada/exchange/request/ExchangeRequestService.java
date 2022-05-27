package hidn.navada.exchange.request;

import hidn.navada.comm.exception.ProductNotFoundException;
import hidn.navada.product.Product;
import hidn.navada.product.ProductJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExchangeRequestService {
    private final ExchangeRequestJpaRepo exchangeRequestJpaRepo;
    private final ProductJpaRepo productJpaRepo;

//    public ExchangeRequest testService() {
//        Product product1=productJpaRepo.getById(1L);
//        Product product2=productJpaRepo.getById(2L);
//
//        ExchangeRequest exchangeRequest=ExchangeRequest.builder()
//                .exchangeProduct(product1)
//                .requestProduct(product2)
//                .build();
//
//        return exchangeRequestJpaRepo.save(exchangeRequest);
//    }

    public ExchangeRequest createExchangeRequest(Long requesterId, Long requestProductId, Long acceptorProductId){

        ExchangeRequest exchangeRequest = new ExchangeRequest();

        // 교환신청 받은 상품
        Product acceptorProduct = productJpaRepo.findById(acceptorProductId).orElseThrow(ProductNotFoundException::new);
        // 교환신청자 상품
        Product requesterProduct = productJpaRepo.findById(requestProductId).orElseThrow(ProductNotFoundException::new);

        exchangeRequest.setAcceptor(acceptorProduct.getUser());
        exchangeRequest.setAcceptorProduct(acceptorProduct);
        exchangeRequest.setRequester(requesterProduct.getUser());
        exchangeRequest.setRequesterProduct(requesterProduct);
//        exchangeRequest.setExchangeStatusCd(0);
        exchangeRequest.setExchangeRequestDt(LocalDateTime.now());

        exchangeRequestJpaRepo.save(exchangeRequest);

        return exchangeRequest;
    }
}
