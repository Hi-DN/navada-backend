package hidn.navada.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hidn.navada.comm.BaseTime;
import hidn.navada.product.category.Category;
import hidn.navada.user.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;                     // pk

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;                          // 해당 상품을 등록한 회원 id(fk)

    @Column(length = 10)
    private String userNickname;                //회원 닉네임

    @Column(length = 20)
    private String productName;                 // 상품 이름

    @Column(length = 200, nullable = false)
    private String productExplanation;          // 상품 설명

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;                  // 상품 카테고리

    @Column(columnDefinition="char default 0") @Builder.Default
    private char productExchangeStatusCd='0';   // 상품 상태(0: 등록, 1: 거래중, 2: 거래 완료)

    @ColumnDefault("0")  @Builder.Default
    private Long heartNum= 0L;                  // 좋아요 수

    private int productCost;                    // 상품 원가

    private int exchangeCostRange;              // 교환 가격 범위

    private String productImageUrl;             // 상품 이미지 주소

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        } else if (!(o instanceof Product)) {
            return false;
        } else {
            return ((Product) o).getProductId().equals(this.productId);
        }
    }
}
