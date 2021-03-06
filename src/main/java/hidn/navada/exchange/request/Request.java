package hidn.navada.exchange.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hidn.navada.comm.BaseTime;
import hidn.navada.product.Product;
import hidn.navada.user.User;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Request extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId; //pk

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "userId",name = "acceptorId",nullable = false)
    private User acceptor;              // 교환수락자(fk)

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "productId",name="acceptorProductId", nullable = false)
    private Product acceptorProduct;   // 수락자 상품(fk)

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "userId",name = "requesterId",nullable = false)
    private User requester;             // 교환신청자(fk)

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "productId",name="requesterProductId", nullable = false)
    private Product requesterProduct;    // 신청자 상품(fk)

    @Builder.Default
    private int exchangeStatusCd=0;    //교환상태(0:교환대기, 1:교환선택, 2:교환거절)
}
