package hidn.navada.image;

import hidn.navada.comm.BaseTime;
import hidn.navada.product.Product;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import static javax.persistence.FetchType.LAZY;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Image extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;   //pk

    @ManyToOne(fetch = LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "productId")
    private Product product;   //상품(fk)

    @Column(nullable = false)
    private String imageUrl;    //이미지 주소

    private int imageDisplayOrder;  //이미지 전시순서
}
