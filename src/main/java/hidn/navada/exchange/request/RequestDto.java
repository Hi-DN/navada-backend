package hidn.navada.exchange.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private int exchangeStatusCd;

//    private String productImage;

    private String productName;

    private String userNickname;
}
