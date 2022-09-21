package hidn.navada.exchange.request;

import hidn.navada.comm.exception.*;
import hidn.navada.exchange.Exchange;
import hidn.navada.exchange.ExchangeService;
import hidn.navada.product.Product;
import hidn.navada.product.ProductJpaRepo;
import hidn.navada.user.User;
import hidn.navada.user.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestService {
    private final RequestJpaRepo requestJpaRepo;
    private final ProductJpaRepo productJpaRepo;
    private final UserJpaRepo userJpaRepo;
    private final ExchangeService exchangeService;

    //교환 신청
    public Request createRequest(Long requesterProductId, Long acceptorProductId){
        Product acceptorProduct = productJpaRepo.findById(acceptorProductId).orElseThrow(ProductNotFoundException::new);
        Product requesterProduct = productJpaRepo.findById(requesterProductId).orElseThrow(ProductNotFoundException::new);

        if(acceptorProduct.getProductExchangeStatusCd() != '0' || requesterProduct.getProductExchangeStatusCd() != '0') {
            throw new ProductExchangeStatusCdDiscrepancyException();
        }

        Request request = Request.builder()
                .acceptor(acceptorProduct.getUser())
                .acceptorProduct(acceptorProduct)
                .requester(requesterProduct.getUser())
                .requesterProduct(requesterProduct)
                .build();

        return requestJpaRepo.save(request);
    }

    //교환 수락
    public Exchange acceptRequest(Long requestId){

        Request request = requestJpaRepo.findById(requestId).orElseThrow(RequestNotFoundException::new);

        // 교환신청상태 변경(대기 -> 수락)
        request.setRequestStatusCd('1');

        // 상품상태 변경(등록완료 -> 교환중)
        Product acceptorProduct = request.getAcceptorProduct();
        acceptorProduct.setProductExchangeStatusCd('1');
        Product requesterProduct = request.getRequesterProduct();
        requesterProduct.setProductExchangeStatusCd('1');

        // acceptor 가 받은 나머지 교환 신청들 거절
        rejectRequestsByAcceptor(acceptorProduct);

        // requester 가 신청한 나머지 교환 신청들 삭제
        rejectRequestsByRequester(requesterProduct);

        // Exchange 엔티티 생성
        return exchangeService.createExchange(request);
    }

    private void rejectRequestsByAcceptor(Product acceptorProduct) {
        List<Request> requestList= requestJpaRepo.findByAcceptorProductAndRequestStatusCd(acceptorProduct,'0');

        //TODO event 생성 후, 알림 가는지 확인 필요!
        for(Request request : requestList){
            request.setRequestStatusCd('2'); //2. 교환 거절
        }
    }

    private void rejectRequestsByRequester(Product requesterProduct) {
        List<Request> requestList= requestJpaRepo.findByRequesterProductAndRequestStatusCd(requesterProduct,'0');
        for(Request request : requestList){
            requestJpaRepo.deleteById(request.getRequestId());
        }
    }

    //교환신청 삭제
    public void deleteRequest(long requestId) {
        Request request = requestJpaRepo.findById(requestId).orElseThrow(RequestNotFoundException::new);

        if(request.getRequestStatusCd() == '0')
            requestJpaRepo.deleteById(requestId);
        else
            throw new ProductExchangeStatusCdDiscrepancyException();
    }

    //내가 신청받은 교환신청 목록 조회
    public Page<RequestDto> getRequestListByRequester(Long userId, Pageable pageable) {
        User requester=userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);

        Page<Request> requestList= requestJpaRepo.findRequestsByRequester(requester,pageable);
        return convertToDto(requestList);
    }

    //내가 신청한 교환신청 목록 조회
    public Page<RequestDto> getRequestListByAcceptor(long userId, List<Character> requestStatusCds, Pageable pageable){
        User acceptor=userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);

        Page<Request> requestList = requestJpaRepo.findRequestsByAcceptor(acceptor, requestStatusCds, pageable);
        return convertToDto(requestList);
    }

    public Page<RequestDto> convertToDto(Page<Request> requestList){
        Page<RequestDto> requestDtoList=requestList.map(RequestDto::new);
        return requestDtoList;
    }

    //교환신청 거절
    public void rejectRequest(Long requestId) {
        Request request= requestJpaRepo.findById(requestId).orElseThrow(RequestNotFoundException::new);
        request.setRequestStatusCd('2'); // 2. 교환거절
    }

    //특정 상품으로부터 받은 교환신청 목록 조회
    public List<RequestDto> getRequestsByCertainProduct(Long productId, Long userId) {
        Product product=productJpaRepo.findById(productId).orElseThrow(ProductNotFoundException::new);
        User acceptor=userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);

        List<Request> requestList=requestJpaRepo.findRequestsByCertainProduct(product,acceptor);

        return requestList.stream().map(RequestDto::new).collect(toList());
    }

    // 교환신청 거절내역 삭제
    public Request deleteDeniedRequest(Long requestId, Boolean isAcceptor) {
        Request request = requestJpaRepo.findById(requestId).orElseThrow(RequestNotFoundException::new);

        if(request.getRequestStatusCd() != '2') throw new RequestStatusCdDiscrepancyException();

        if(isAcceptor) request.setAcceptorDeniedRequestDeleteYn(true);
        else request.setRequesterDeniedRequestDeleteYn(true);

        return request;
    }
}
