package hidn.navada.exchange.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hidn.navada.product.Product;
import hidn.navada.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRequestParams {

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "productId",name="acceptorProductId", nullable = false)
    private Long acceptorProductId;   // 수락자 상품(fk)

    @JsonIgnore @ManyToOne(fetch = LAZY)
    @JoinColumn(referencedColumnName = "productId",name="requestProductId", nullable = false)
    private Long requesterProductId;    // 신청자 상품(fk)

}
