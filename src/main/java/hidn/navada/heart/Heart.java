package hidn.navada.heart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hidn.navada.comm.BaseTime;
import hidn.navada.product.Product;
import hidn.navada.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Heart extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heartId;                   // pk

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;                      // 하트를 누른 회원 id(fk)

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;                // 하트가 눌려진 상품 id(fk)
}
