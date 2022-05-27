package hidn.navada.exchange.request;

import hidn.navada.comm.exception.ExchangeRequestNotFoundException;
import hidn.navada.comm.exception.ProductNotFoundException;
import hidn.navada.exchange.Exchange;
import hidn.navada.exchange.ExchangeService;
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
        exchangeRequest.setExchangeRequestDt(LocalDateTime.now());

        exchangeRequestJpaRepo.save(exchangeRequest);

        return exchangeRequest;
    }

//    public Exchange acceptExchangeRequest(Long exchangeRequestId){
//
//        ExchangeRequest exchangeRequest = exchangeRequestJpaRepo.findById(exchangeRequestId).orElseThrow(ExchangeRequestNotFoundException::new);
//
//        // [교환신청 수락 시 변경사항]
//        // 교환신청상태 변경
//        exchangeRequest.setExchangeStatusCd(1);
//        exchangeRequestJpaRepo.save(exchangeRequest);
//
//        // 상품상태 변경
//        // product.productStatusCd=1 (두 상품 모두) (0=등록완료, 1=교환중, 2=교환완료)
//        Product product = exchangeRequest.getAcceptorProduct();
////        product.
//        // Exchange 엔티티 생성
//
//
//    }

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
}
