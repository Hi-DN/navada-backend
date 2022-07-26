package hidn.navada.exchange.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private long requestId;

    private int exchangeStatusCd;   //교환상태(0 : 대기 , 1 : 수락, 2 : 거절)

    private String acceptorNickname;    //수락자 닉네임
    private String requesterNickName;   //요청자 닉네임

    private String acceptorProductName;     // 수락자 상품명
    private String requesterProductName;    // 요청자 상품명

    private int acceptorProductCost;    //수락자 상품원가
    private int requesterProductCost;   //요청자 상품원가

    private int acceptorProductCostRange;   //수락자 상품교환가 범위
    private int requesterProductCostRange;  //요청자 상품교환가 범위

    //    private String acceptorProductImage;
    //    private String requesterProductImage;

    public RequestDto(Request request){
        requestId=request.getRequestId();
        exchangeStatusCd=request.getExchangeStatusCd();

        acceptorProductName = request.getAcceptorProduct().getProductName();
        requesterProductName = request.getRequesterProduct().getProductName();

        acceptorProductCost=request.getAcceptorProduct().getProductCost();
        requesterProductCost=request.getRequesterProduct().getProductCost();

        acceptorProductCostRange=request.getAcceptorProduct().getExchangeCostRange();
        requesterProductCostRange=request.getRequesterProduct().getExchangeCostRange();

        acceptorNickname=request.getAcceptor().getUserNickname();
        requesterNickName=request.getRequester().getUserNickname();
    }
}
