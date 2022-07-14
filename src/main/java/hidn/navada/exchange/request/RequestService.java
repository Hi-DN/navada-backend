package hidn.navada.exchange.request;

import hidn.navada.comm.exception.RequestNotFoundException;
import hidn.navada.comm.exception.ProductNotFoundException;
import hidn.navada.comm.exception.ProductStatusCdDiscrepancyException;
import hidn.navada.exchange.Exchange;
import hidn.navada.exchange.ExchangeService;
import hidn.navada.product.Product;
import hidn.navada.product.ProductJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestJpaRepo requestJpaRepo;
    private final ProductJpaRepo productJpaRepo;
    private final ExchangeService exchangeService;

    //교환 신청
    public Request createRequest(Long requesterId, Long requestProductId, Long acceptorProductId){

        Request request = new Request();

        // 교환신청 받은 상품
        Product acceptorProduct = productJpaRepo.findById(acceptorProductId).orElseThrow(ProductNotFoundException::new);
        // 교환신청자 상품
        Product requesterProduct = productJpaRepo.findById(requestProductId).orElseThrow(ProductNotFoundException::new);

        if(acceptorProduct.getProductStatusCd() != 0 || requesterProduct.getProductStatusCd() != 0) {
            throw new ProductStatusCdDiscrepancyException();
        }

        request.setAcceptor(acceptorProduct.getUser());
        request.setAcceptorProduct(acceptorProduct);
        request.setRequester(requesterProduct.getUser());
        request.setRequesterProduct(requesterProduct);

        requestJpaRepo.save(request);

        return request;
    }

    //교환 수락
    public Exchange acceptRequest(Long requestId){

        Request request = requestJpaRepo.findById(requestId).orElseThrow(RequestNotFoundException::new);

        // 교환신청상태 변경(대기 -> 수락)
        request.setExchangeStatusCd(1);
        requestJpaRepo.save(request);

        // 상품상태 변경(등록완료 -> 교환중)
        Product acceptorProduct = request.getAcceptorProduct();
        Product requesterProduct = request.getRequesterProduct();
        acceptorProduct.setProductStatusCd(1);
        requesterProduct.setProductStatusCd(1);
        productJpaRepo.save(acceptorProduct);
        productJpaRepo.save(requesterProduct);

        //나머지 교환 신청 거절
        rejectOtherRequests(acceptorProduct);

        // Exchange 엔티티 생성
        return exchangeService.createExchange(request);
    }

    private void rejectOtherRequests(Product acceptorProduct) {
        List<Request> requestList= requestJpaRepo.findByAcceptorProductAndExchangeStatusCd(acceptorProduct,0);

        //TODO event 생성 후, 알림 가는지 확인 필요!
        for(Request request : requestList){
            request.setExchangeStatusCd(2); //2. 교환 거절
        }
    }

    //교환신청 삭제
    public Boolean deleteRequest(long requestId) {
        Request request = requestJpaRepo.findById(requestId).orElseThrow(RequestNotFoundException::new);
        if(request.getExchangeStatusCd() == 0) {
            // 교환신청 상태가 대기중일 시에만 삭제
            requestJpaRepo.deleteById(requestId);
            return true;
        }
        else {
            return false;
        }
    }

    public List<Request> getRequestList(Long userId) {
        return requestJpaRepo.findByRequesterUserId(userId);
    }

    //교환신청 거절
    public void rejectRequest(Long requestId) {
        Request request= requestJpaRepo.findById(requestId).orElseThrow(RequestNotFoundException::new);
        request.setExchangeStatusCd(2); // 2. 교환거절
    }
}
