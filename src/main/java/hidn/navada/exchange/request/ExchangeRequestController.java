package hidn.navada.exchange.request;

import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExchangeRequestController {
    private final ResponseService responseService;
    private final ExchangeRequestService exchangeRequestService;

    // 교환신청 등록
    @PostMapping(value = "/user/{userId}/exchange/{requestProductId}/request/{acceptorProductId}")
    public SingleResponse<ExchangeRequest> createExchangeRequest(@PathVariable Long userId, @PathVariable Long requestProductId, @PathVariable Long acceptorProductId){
        // userId 필요?, params로 받을지 pathvariable로 받을지
        return responseService.getSingleResponse(exchangeRequestService.createExchangeRequest(userId, requestProductId, acceptorProductId));
    }

    // 교환신청 수락
//    - [교환신청 수락 시 변경사항]
//            - exchangeRequest.exchangeStatusCd=1(0=대기, 1=수락, 2=거절)
//            - product.productStatusCd=1 (두 상품 모두) (0=등록완료, 1=교환중, 2=교환완료)
//            - Exchange 엔티티 생성

    // 교환신청 목록 조회

    // 교환신청 취소


//    @PostMapping(value = "/exchange/request")
//    public ExchangeRequest testController(){
//        return exchangeRequestService.testService();
//    }
}
