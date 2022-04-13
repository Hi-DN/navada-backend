package hidn.navada.exchange.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExchangeRequestController {
    private final ExchangeRequestService exchangeRequestService;

    @PostMapping(value = "/exchange/request")
    public ExchangeRequest testController(){
        return exchangeRequestService.testService();
    }
}
