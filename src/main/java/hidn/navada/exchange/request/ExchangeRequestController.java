package hidn.navada.exchange.request;

import hidn.navada.comm.response.CommonResponse;
import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import hidn.navada.exchange.Exchange;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @PatchMapping(value = "/user/exchange/request/{exchangeRequestId}")
    public SingleResponse<Exchange> createExchangeRequest(@PathVariable Long exchangeRequestId){
        return responseService.getSingleResponse(exchangeRequestService.acceptExchangeRequest(exchangeRequestId));
    }

    // 교환신청 목록 조회
    // exchangeRequestStatus, productImage, productName, userNickname 따로 묶어서 만들어야할지?
//    @GetMapping(value = "/user/{userId}/exchange/request")
//    public SingleResponse<Exchange> getExchangeRequestList(@PathVariable Long userId){
//
//    }

    // 교환신청 취소
    @DeleteMapping(value="/user/exchange/request/{exchangeRequestId}")
    public CommonResponse deleteExchangeRequest(@PathVariable Long exchangeRequestId){
        if(exchangeRequestService.deleteExchangeRequest(exchangeRequestId))
            return responseService.getSuccessResponse();
        else
            return responseService.getErrorResponse(-1, "FAIL: 교환신청 상태가 대기중이 아닙니다.");
    }
}
