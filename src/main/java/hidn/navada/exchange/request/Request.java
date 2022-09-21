package hidn.navada.exchange.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hidn.navada.comm.BaseTime;
import hidn.navada.product.Product;
import hidn.navada.user.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
    private Long requestId;             //pk

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "userId",name = "acceptorId",nullable = false)
    private User acceptor;              // 교환수락자(fk)

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "productId",name="acceptorProductId", nullable = false)
    private Product acceptorProduct;    // 수락자 상품(fk)

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "userId",name = "requesterId",nullable = false)
    private User requester;             // 교환신청자(fk)

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "productId",name="requesterProductId", nullable = false)
    private Product requesterProduct;   // 신청자 상품(fk)

    @Column(columnDefinition="char default 0") @Builder.Default
    private char requestStatusCd='0';     // 교환상태(0:교환대기, 1:교환선택, 2:교환거절)

    @ColumnDefault("false") @Builder.Default
    private boolean acceptorDeniedRequestDeleteYn=false;    // 수락자 교환신청 거절내역 삭제여부

    @ColumnDefault("false") @Builder.Default
    private boolean requesterDeniedRequestDeleteYn=false;   // 신청자 교환신청 거절내역 삭제여부
}
