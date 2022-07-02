package hidn.navada.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hidn.navada.comm.BaseTime;
import hidn.navada.comm.enums.Category;
import hidn.navada.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;                 // pk

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;                      // 해당 상품을 등록한 회원 id(fk)

    @Column(length = 20)
    private String productName;             // 상품 이름

    @Column(length = 100, nullable = false)
    private String productExplanation;      // 상품 설명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;              // 상품 카테고리

    @Builder.Default
    private int productStatusCd=0;          // 상품 상태(0: 등록, 1: 거래중, 2: 거래 완료)

    @Builder.Default
    private Long heartNum= 0L;              // 좋아요 수

    private int productCost;                // 상품 원가

    private int exchangeCostRange;          // 교환 가격 범위

}
