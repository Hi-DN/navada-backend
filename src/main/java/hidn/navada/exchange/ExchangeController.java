package hidn.navada.exchange;

import hidn.navada.comm.response.ListResponse;
import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class ExchangeController {
    private final ResponseService responseService;
    private final ExchangeService exchangeService;


    // 교환 완료
    @PatchMapping(value = "/user/exchange/{exchangeId}")
    public SingleResponse<Exchange> completeExchange(@PathVariable Long exchangeId, @RequestParam(name="acceptoryn") Boolean isAcceptor){
        return responseService.getSingleResponse(exchangeService.completeExchange(exchangeId, isAcceptor));
    }

    // 교환 목록 조회
    @GetMapping(value = "/user/{userId}/exchanges")
    public ListResponse<Exchange> getExchangeList(@PathVariable Long userId,  @RequestParam(name="completeyn") Boolean isComplete) {
        return responseService.getListResponse(exchangeService.getExchangeList(userId, isComplete));
    }

    // 교환상대에게 평점 부여
    @PatchMapping(value = "/user/exchange/{exchangeId}/rate")
    public SingleResponse<Exchange> rateExchange(@PathVariable Long exchangeId, @RequestParam(name="acceptoryn") Boolean isAcceptor, @RequestParam(name="rating") float rating){
        return responseService.getSingleResponse(exchangeService.rateExchange(exchangeId, isAcceptor, rating));
    }

    // 교환내역 삭제 api
    @PatchMapping(value = "/user/exchange/{exchangeId}/dlthst")
    public SingleResponse<Exchange> deleteExchangeHistory(@PathVariable Long exchangeId, @RequestParam(name="acceptoryn") Boolean isAcceptor){
        return responseService.getSingleResponse(exchangeService.deleteExchangeHistory(exchangeId, isAcceptor));
    }
}
