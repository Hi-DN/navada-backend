package hidn.navada.exchange.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hidn.navada.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
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
public class ExchangeRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exchangeRequestId; //pk

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "productId",name="exchangeProductId")
    private Product exchangeProduct;   //원하는 상품(fk)

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "productId",name="requestProductId")
    private Product requestProduct;    //제시한 상품(fk)

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime exchangeRequestDt;    //교환신청일시

    @Builder.Default
    private int exchangeStatusCd=0;    //교환상태(0:교환대기, 1:교환선택, 2:교환거절)
}
