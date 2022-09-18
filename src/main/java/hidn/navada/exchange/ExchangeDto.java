package hidn.navada.exchange;

import hidn.navada.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeDto {
    private long exchangeId;

    private long requesterId;

    private long acceptorId;

    private boolean requesterConfirmYn;

    private boolean acceptorConfirmYn;

    private Product requesterProduct;

    private Product acceptorProduct;

    public ExchangeDto(Exchange exchange){
        exchangeId=exchange.getExchangeId();
        requesterId = exchange.getRequester().getUserId();
        acceptorId = exchange.getAcceptor().getUserId();
        requesterConfirmYn = exchange.isRequesterConfirmYn();
        acceptorConfirmYn = exchange.isAcceptorConfirmYn();
        requesterProduct = exchange.getRequesterProduct();
        acceptorProduct = exchange.getAcceptorProduct();
    }
}
