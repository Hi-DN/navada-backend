package hidn.navada.exchange;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hidn.navada.comm.BaseTime;
import hidn.navada.product.Product;
import hidn.navada.user.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Exchange extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exchangeId;                //pk

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "userId",name = "acceptorId",nullable = false)
    private User acceptor;                  //교환수락자(fk)

    @OneToOne(fetch = LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(referencedColumnName = "productId",name = "acceptorProductId",nullable = false)
    private Product acceptorProduct;        //수락자상품(fk)

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "userId",name = "requesterId",nullable = false)
    private User requester;                 //교환신청자(fk)

    @OneToOne(fetch = LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(referencedColumnName = "productId",name = "requesterProductId",nullable = false)
    private Product requesterProduct;       //신청자상품(fk)

    @ColumnDefault("false") @Builder.Default
    private boolean acceptorConfirmYn=false;            //수락자 확인여부

    @ColumnDefault("false") @Builder.Default
    private boolean requesterConfirmYn=false;           //신청자 확인여부

    private LocalDateTime exchangeCompleteDt;           //교환완료일시

    @ColumnDefault("-1") @Builder.Default
    private float acceptorRating=-1;                      //acceptor가 받은 평점

    @ColumnDefault("-1") @Builder.Default
    private float requesterRating=-1;                     //requester가 받은 평점

    @ColumnDefault("false") @Builder.Default
    private boolean acceptorHistoryDeleteYn=false;      //acceptor의 거래내역 삭제 여부

    @ColumnDefault("false") @Builder.Default
    private boolean requesterHistoryDeleteYn=false;     //requester의 거래내역 삭제 여부

    @Column(columnDefinition="char default 1")
    @Builder.Default
    private char exchangeStatusCd='1';   // 교환 상태(1: 교환중, 2: 교환완료, 3: 교환취소)


    //==수정 로직==//
    public void deleteAccepter() { this.acceptor = null; }
    public void deleteRequester() { this.requester = null; }
}
