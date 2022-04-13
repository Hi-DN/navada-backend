package hidn.navada.exchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Exchange {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exchangeId;    //pk

    //fixme 아래 4개 컬럼
    private long acceptor;          //교환수락자(fk)

    private long acceptorProduct;   //수락자상품(fk)

    private long requester;         //교환신청자(fk)

    private long requesterProduct;  //신청자상품(fk)

    private boolean acceptorConfirmYn;          //수락자 확인여부

    private boolean requesterConfirmYn;         //신청자 확인여부

    private boolean exchangeCompleteYn;         //교환완료여부

    private LocalDateTime exchangeCompleteDt;   //교환완료일시
}
