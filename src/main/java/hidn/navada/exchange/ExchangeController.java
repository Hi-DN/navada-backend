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


    // 교환 완료
//    - [한쪽(수락자 또는 요청자)가 교환완료를 누른 경우 변경사항]
//            - xxxConfirmYn=true
//            - [처리 후 두명 다 완료라면]
//            - exchange.exchangeCompleteYn=true;
//            - exchange.exchangeCompleteDt=now();
//            - product.productStatusCd=2(교환완료); (두 상품 모두)

    //

    // 교환 목록 조회

    @PostMapping(value = "/exchange")
    public SingleResponse<Exchange> testController(){
        return responseService.getSingleResponse(exchangeService.testService());
    }
}
