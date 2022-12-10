package hidn.navada.exchange;

import hidn.navada.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeDto {
    private long exchangeId;

    private long requesterId;

    private long acceptorId;

    private boolean requesterConfirmYn;

    private boolean acceptorConfirmYn;

    private float requesterRating;

    private float acceptorRating;

    private Product requesterProduct;

    private Product acceptorProduct;

    private char exchangeStatusCd;

    private LocalDateTime exchangeCompleteDt;

    public ExchangeDto(Exchange exchange){
        exchangeId=exchange.getExchangeId();
        requesterId = exchange.getRequester().getUserId();
        acceptorId = exchange.getAcceptor().getUserId();
        requesterConfirmYn = exchange.isRequesterConfirmYn();
        acceptorConfirmYn = exchange.isAcceptorConfirmYn();
        requesterRating = exchange.getRequesterRating();
        acceptorRating = exchange.getAcceptorRating();
        requesterProduct = exchange.getRequesterProduct();
        acceptorProduct = exchange.getAcceptorProduct();
        exchangeStatusCd = exchange.getExchangeStatusCd();
        exchangeCompleteDt = exchange.getExchangeCompleteDt();
    }
}
