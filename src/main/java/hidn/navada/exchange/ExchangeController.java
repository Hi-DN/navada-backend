package hidn.navada.exchange;

import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExchangeController {
    private final ResponseService responseService;
    private final ExchangeService exchangeService;

    @PostMapping(value = "/exchange")
    public SingleResponse<Exchange> testController(){
        return responseService.getSingleResponse(exchangeService.testService());
    }
}
