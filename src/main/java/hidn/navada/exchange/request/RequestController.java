package hidn.navada.exchange.request;

import hidn.navada.comm.response.CommonResponse;
import hidn.navada.comm.response.ListResponse;
import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import hidn.navada.exchange.Exchange;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class RequestController {
    private final ResponseService responseService;
    private final RequestService requestService;

    // 교환신청 등록
    @PostMapping(value = "/user/{userId}/exchange/{requestProductId}/request/{acceptorProductId}")
    public SingleResponse<Request> createRequest(@PathVariable Long userId, @PathVariable Long requestProductId, @PathVariable Long acceptorProductId){
        // userId 필요?, params로 받을지 pathvariable로 받을지
        return responseService.getSingleResponse(requestService.createRequest(userId, requestProductId, acceptorProductId));
    }

    // 교환신청 수락
    @PatchMapping(value = "/user/exchange/request/{requestId}")
    public SingleResponse<Exchange> createRequest(@PathVariable Long requestId){
        return responseService.getSingleResponse(requestService.acceptRequest(requestId));
    }

    // 내가 신청한 교환신청 목록 조회1 (네비게이션에서 사용)
    /* 이전 dto 호출 방식 : 추후 더 나은 방식으로 교체
    @GetMapping(value = "/requester/{userId}/exchange/requests")
    public ListResponse<RequestDto> getRequestListByRequester(@PathVariable Long userId){
        List<Request> ers = requestService.getRequestListByRequester(userId);
        List<RequestDto> result = new ArrayList<>();
        ers.forEach(er -> {
            RequestDto dto = new RequestDto(er.getExchangeStatusCd(), er.getRequesterProduct().getProductName(), er.getAcceptor().getUserNickname());
            result.add(dto);
        });

        return responseService.getListResponse(result);
    }
    */

    // 내가 신청한 교환신청 목록 조회2 (네비게이션에서 사용)
    @GetMapping(value = "/requester/{userId}/exchange/requests")
    public ListResponse<RequestDto> getRequestListByRequester(@PathVariable Long userId){
        return responseService.getListResponse(requestService.getRequestListByRequester(userId));
    }

    // 내가 신청받은 교환신청 목록 조회 (홈에서 사용)
    @GetMapping(value = "/acceptor/{userId}/exchange/requests")
    public ListResponse<RequestDto> getRequestListByAcceptor(@PathVariable long userId, @RequestParam List<Integer> exchangeStatusCd){
        return responseService.getListResponse(requestService.getRequestListByAcceptor(userId,exchangeStatusCd));
    }

    // 교환신청 취소
    @DeleteMapping(value="/user/exchange/request/{requestId}")
    public CommonResponse deleteRequest(@PathVariable Long requestId){
        if(requestService.deleteRequest(requestId))
            return responseService.getSuccessResponse();
        else
            return responseService.getErrorResponse(-1, "FAIL: 교환신청 상태가 대기중이 아닙니다.");
    }

    //교환신청 거절
    @PatchMapping(value = "/exchange/request/{requestId}/reject")
    public CommonResponse rejectRequest(@PathVariable Long requestId){
        requestService.rejectRequest(requestId);
        //TODO event 생성 후, 알림 가는지 확인 필요!
        return responseService.getSuccessResponse();
    }
}
