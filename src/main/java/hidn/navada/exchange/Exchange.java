package hidn.navada.exchange;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hidn.navada.comm.BaseTime;
import hidn.navada.product.Product;
import hidn.navada.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Exchange extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exchangeId;    //pk

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "userId",name = "acceptorId",nullable = false)
    private User acceptor;          //교환수락자(fk)

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "productId",name = "acceptorProductId",nullable = false)
    private Product acceptorProduct;   //수락자상품(fk)

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "userId",name = "requesterId",nullable = false)
    private User requester;         //교환신청자(fk)

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "productId",name = "requesterProductId",nullable = false)
    private Product requesterProduct;  //신청자상품(fk)

    private boolean acceptorConfirmYn;          //수락자 확인여부

    private boolean requesterConfirmYn;         //신청자 확인여부

    private boolean exchangeCompleteYn;         //교환완료여부

    private LocalDateTime exchangeCompleteDt;   //교환완료일시
}
