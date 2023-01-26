package hidn.navada.exchange;

import hidn.navada.comm.response.PageResponse;
import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class ExchangeController {
    private final ResponseService responseService;
    private final ExchangeService exchangeService;


    // 교환 완료
    @PatchMapping(value = "/exchange/{exchangeId}")
    public SingleResponse<Exchange> completeExchange(@PathVariable Long exchangeId, @RequestParam Boolean isAcceptor){
        return responseService.getSingleResponse(exchangeService.completeExchange(exchangeId, isAcceptor));
    }

    // 교환 목록 조회
    @GetMapping(value = "/user/{userId}/exchanges")
    public PageResponse<ExchangeDto> getExchangeList(@PathVariable Long userId,
                                                     @RequestParam(required = false) List<Character> exchangeStatusCds,
                                                     @RequestParam(required = false) Boolean viewOnlySentElseGot,
                                                     @PageableDefault(size =20, sort = "exchangeStatusCd") Pageable pageable) {
        return responseService.getPageResponse(exchangeService.getExchangeList(userId, exchangeStatusCds, viewOnlySentElseGot, pageable));
    }

    // 교환상대에게 평점 부여
    @PatchMapping(value = "/exchange/{exchangeId}/rate")
    public SingleResponse<Exchange> rateExchange(@PathVariable Long exchangeId, @RequestParam Boolean isAcceptor, @RequestParam float rating){
        return responseService.getSingleResponse(exchangeService.rateExchange(exchangeId, isAcceptor, rating));
    }

    // 교환내역 삭제 api
    @PatchMapping(value = "/exchange/{exchangeId}/delete")
    public SingleResponse<Exchange> deleteExchangeHistory(@PathVariable Long exchangeId, @RequestParam Boolean isAcceptor){
        return responseService.getSingleResponse(exchangeService.deleteExchangeHistory(exchangeId, isAcceptor));
    }

    // 교환 취소
    @PatchMapping(value = "/user/{userId}/exchange/{exchangeId}/cancel")
    public SingleResponse<Exchange> cancelExchange(@PathVariable Long userId, @PathVariable Long exchangeId){
        return responseService.getSingleResponse(exchangeService.cancelExchange(userId,exchangeId));
    }

}
