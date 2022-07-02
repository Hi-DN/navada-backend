package hidn.navada.exchange.request;

import hidn.navada.comm.response.CommonResponse;
import hidn.navada.comm.response.ListResponse;
import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import hidn.navada.exchange.Exchange;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    @GetMapping(value = "/user/{userId}/exchange/requests")
    public ListResponse<ExchangeRequestDto> getExchangeRequestList(@PathVariable Long userId){
        List<ExchangeRequest> ers = exchangeRequestService.getExchangeRequestList(userId);
        List<ExchangeRequestDto> result = new ArrayList<>();
        ers.forEach(er -> {
            ExchangeRequestDto dto = new ExchangeRequestDto(er.getExchangeStatusCd(), er.getRequesterProduct().getProductName(), er.getAcceptor().getUserNickname());
            result.add(dto);
        });

        return responseService.getListResponse(result);
    }

    // 교환신청 취소
    @DeleteMapping(value="/user/exchange/request/{exchangeRequestId}")
    public CommonResponse deleteExchangeRequest(@PathVariable Long exchangeRequestId){
        if(exchangeRequestService.deleteExchangeRequest(exchangeRequestId))
            return responseService.getSuccessResponse();
        else
            return responseService.getErrorResponse(-1, "FAIL: 교환신청 상태가 대기중이 아닙니다.");
    }

    //교환신청 거절
    @PatchMapping(value = "/exchange/request/{exchangeRequestId}/reject")
    public CommonResponse rejectExchangeRequest(@PathVariable Long exchangeRequestId){
        exchangeRequestService.rejectExchangeRequest(exchangeRequestId);
        //TODO event 생성 후, 알림 가는지 확인 필요!
        return responseService.getSuccessResponse();
    }
}
