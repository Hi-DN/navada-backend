package hidn.navada.exchange.request;

import hidn.navada.product.Product;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private long requestId;

    private char requestStatusCd;       //신청상태(0 : 대기 , 1 : 수락, 2 : 거절)

    private Product requesterProduct;

    private Product acceptorProduct;

    public RequestDto(Request request){
        requestId=request.getRequestId();
        requestStatusCd=request.getRequestStatusCd();
        requesterProduct = request.getRequesterProduct();
        acceptorProduct = request.getAcceptorProduct();
    }
}
