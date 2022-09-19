package hidn.navada.exchange.request;

import hidn.navada.comm.response.*;
import hidn.navada.exchange.Exchange;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class RequestController {
    private final ResponseService responseService;
    private final RequestService requestService;

    // 교환신청 등록
    @PostMapping(value = "/exchange/request/{requesterProductId}/{acceptorProductId}")
    public SingleResponse<Request> createRequest(@PathVariable Long requesterProductId, @PathVariable Long acceptorProductId){
        return responseService.getSingleResponse(requestService.createRequest(requesterProductId, acceptorProductId));
    }

    // 교환신청 수락
    @PatchMapping(value = "/exchange/request/{requestId}")
    public SingleResponse<Exchange> createRequest(@PathVariable Long requestId){
        return responseService.getSingleResponse(requestService.acceptRequest(requestId));
    }

    // 내가 신청한 교환신청 목록 조회 (네비게이션에서 사용)
    @GetMapping(value = "/requester/{userId}/exchange/requests")
    public PageResponse<RequestDto> getRequestListByRequester(@PathVariable Long userId,
                                                              @PageableDefault(size =20, sort = "requestStatusCd") Pageable pageable){
        return responseService.getPageResponse(requestService.getRequestListByRequester(userId,pageable));
    }

    // 내가 신청받은 교환신청 목록 조회 (홈에서 사용)
    @GetMapping(value = "/acceptor/{userId}/exchange/requests")
    public PageResponse<RequestDto> getRequestListByAcceptor(@PathVariable long userId, @RequestParam List<Character> requestStatusCd,
                                                             @PageableDefault(size =20) Pageable pageable){
        return responseService.getPageResponse(requestService.getRequestListByAcceptor(userId,requestStatusCd,pageable));
    }

    // 교환신청 취소
    @DeleteMapping(value="/exchange/request/{requestId}")
    public CommonResponse deleteRequest(@PathVariable Long requestId){
        requestService.deleteRequest(requestId);
        return responseService.getSuccessResponse();
    }

    //교환신청 거절
    @PatchMapping(value = "/exchange/request/{requestId}/reject")
    public CommonResponse rejectRequest(@PathVariable Long requestId){
        requestService.rejectRequest(requestId);
        //TODO event 생성 후, 알림 가는지 확인 필요!
        return responseService.getSuccessResponse();
    }

    // 특정 상품으로부터 받은 교환신청 목록 조회
    @GetMapping(value = "/product/{productId}/exchange/request")
    public ListResponse<RequestDto> getRequestsByCertainProduct(@PathVariable Long productId, @RequestParam Long userId){
        return responseService.getListResponse(requestService.getRequestsByCertainProduct(productId,userId));
    }

    // 교환신청 거절내역 삭제
    @PatchMapping(value = "/exchange/request/{requestId}/delete")
    public SingleResponse<Request> deleteDeniedRequest(@PathVariable Long requestId, @RequestParam Boolean isAcceptor) {
        return responseService.getSingleResponse(requestService.deleteDeniedRequest(requestId, isAcceptor));
    }
}
