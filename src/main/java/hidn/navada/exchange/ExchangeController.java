package hidn.navada.exchange;

import hidn.navada.comm.response.ListResponse;
import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ExchangeController {
    private final ResponseService responseService;
    private final ExchangeService exchangeService;


    // 교환 완료
    @PatchMapping(value = "/user/exchange/{exchangeId}")
    public SingleResponse<Exchange> completeExchange(@PathVariable Long exchangeId, @RequestParam(name="acceptor") Boolean isAcceptor){
        return responseService.getSingleResponse(exchangeService.completeExchange(exchangeId, isAcceptor));
    }

    // 교환 목록 조회
    @GetMapping(value = "/user/{userId}/exchanges")
    public ListResponse<Exchange> getExchangeList(@PathVariable Long userId, @RequestParam(name="acceptor") Boolean isAcceptor, @RequestParam(name="complete") Boolean isComplete) {
        return responseService.getListResponse(exchangeService.getExchangeList(userId, isAcceptor, isComplete));
    }
}
