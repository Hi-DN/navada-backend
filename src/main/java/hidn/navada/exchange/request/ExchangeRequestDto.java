package hidn.navada.exchange.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRequestDto {
    private int exchangeRequestStatusCd;

//    private String productImage;

    private String productName;

    private String userNickname;
}
