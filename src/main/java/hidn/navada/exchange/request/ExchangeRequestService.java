package hidn.navada.exchange.request;

import hidn.navada.comm.exception.ExchangeRequestNotFoundException;
import hidn.navada.comm.exception.ProductNotFoundException;
import hidn.navada.comm.exception.ProductStatusCdDiscrepancyException;
import hidn.navada.exchange.Exchange;
import hidn.navada.exchange.ExchangeService;
import hidn.navada.product.Product;
import hidn.navada.product.ProductJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeRequestService {
    private final ExchangeRequestJpaRepo exchangeRequestJpaRepo;
    private final ProductJpaRepo productJpaRepo;
    private final ExchangeService exchangeService;

    public ExchangeRequest createExchangeRequest(Long requesterId, Long requestProductId, Long acceptorProductId){

        ExchangeRequest exchangeRequest = new ExchangeRequest();

        // 교환신청 받은 상품
        Product acceptorProduct = productJpaRepo.findById(acceptorProductId).orElseThrow(ProductNotFoundException::new);
        // 교환신청자 상품
        Product requesterProduct = productJpaRepo.findById(requestProductId).orElseThrow(ProductNotFoundException::new);

        if(acceptorProduct.getProductStatusCd() != 0 || requesterProduct.getProductStatusCd() != 0) {
            throw new ProductStatusCdDiscrepancyException();
        }

        exchangeRequest.setAcceptor(acceptorProduct.getUser());
        exchangeRequest.setAcceptorProduct(acceptorProduct);
        exchangeRequest.setRequester(requesterProduct.getUser());
        exchangeRequest.setRequesterProduct(requesterProduct);

        exchangeRequestJpaRepo.save(exchangeRequest);

        return exchangeRequest;
    }

    public Exchange acceptExchangeRequest(Long exchangeRequestId){

        ExchangeRequest exchangeRequest = exchangeRequestJpaRepo.findById(exchangeRequestId).orElseThrow(ExchangeRequestNotFoundException::new);

        // 교환신청상태 변경(대기 -> 수락)
        exchangeRequest.setExchangeStatusCd(1);
        exchangeRequestJpaRepo.save(exchangeRequest);

        // 상품상태 변경(등록완료 -> 교환중)
        //TODO: 프로덕트 서비스에서 변경
        Product acceptorProduct = exchangeRequest.getAcceptorProduct();
        Product requesterProduct = exchangeRequest.getRequesterProduct();
        acceptorProduct.setProductStatusCd(1);
        requesterProduct.setProductStatusCd(1);
        productJpaRepo.save(acceptorProduct);
        productJpaRepo.save(requesterProduct);

        //나머지 교환 신청 거절
        rejectOtherRequests(acceptorProduct);

        // Exchange 엔티티 생성
        return exchangeService.createExchange(exchangeRequest);
    }

    private void rejectOtherRequests(Product acceptorProduct) {
        List<ExchangeRequest> exchangeRequestList= exchangeRequestJpaRepo.findByRequesterProductAndExchangeStatusCd(acceptorProduct,0);

        for(ExchangeRequest exchangeRequest : exchangeRequestList){
            exchangeRequest.setExchangeStatusCd(2); //2. 교환 거절
        }
    }

    public Boolean deleteExchangeRequest(long exchangeRequestId) {
        ExchangeRequest exchangeRequest = exchangeRequestJpaRepo.findById(exchangeRequestId).orElseThrow(ExchangeRequestNotFoundException::new);
        if(exchangeRequest.getExchangeStatusCd() == 0) {
            // 교환신청 상태가 대기중일 시에만 삭제
            exchangeRequestJpaRepo.deleteById(exchangeRequestId);
            return true;
        }
        else {
            return false;
        }
    }

    public List<ExchangeRequest> getExchangeRequestList(Long userId) {
        return exchangeRequestJpaRepo.findByRequesterUserId(userId);
    }

    //교환신청 거절
    public void rejectExchangeRequest(Long exchangeRequestId) {
        ExchangeRequest exchangeRequest=exchangeRequestJpaRepo.findById(exchangeRequestId).orElseThrow(ExchangeRequestNotFoundException::new);
        exchangeRequest.setExchangeStatusCd(2); // 2. 교환거절
    }
}
