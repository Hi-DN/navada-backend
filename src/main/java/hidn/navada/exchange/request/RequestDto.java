package hidn.navada.exchange.request;

import hidn.navada.product.Product;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private long requestId;

    private char requestStatusCd;       //신청상태(0 : 대기 , 1 : 수락, 2 : 거절)

    private LocalDateTime requestCreatedDt;     // 교환 신청 일자

    private Product requesterProduct;

    private Product acceptorProduct;

    public RequestDto(Request request){
        requestId=request.getRequestId();
        requestStatusCd=request.getRequestStatusCd();
        requestCreatedDt = request.getCreatedDate();
        requesterProduct = request.getRequesterProduct();
        acceptorProduct = request.getAcceptorProduct();
    }
}
