package hidn.navada.exchange.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private long requestId;

    private int exchangeStatusCd;   //교환상태(0 : 대기 , 1 : 수락, 2 : 거절)

    private String acceptorProductName;     // 수락자 상품명
    private String requesterProductName;    // 요청자 상품명

    private String acceptorNickname;    //수락자 닉네임
    private String requesterNickName;   //요청자 닉네임

    //    private String acceptorProductImage;
    //    private String requesterProductImage;

    public RequestDto(Request request){
        requestId=request.getRequestId();
        exchangeStatusCd=request.getExchangeStatusCd();
        acceptorProductName = request.getAcceptorProduct().getProductName();
        requesterProductName = request.getRequesterProduct().getProductName();
        acceptorNickname=request.getAcceptor().getUserNickname();
        requesterNickName=request.getRequester().getUserNickname();
    }
}
