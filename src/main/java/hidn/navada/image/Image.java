package hidn.navada.image;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;   //pk

    //fixme
    private long product;   //상품(fk)

    @Column(nullable = false)
    private String imageUrl;    //이미지 주소

    private int imageDisplayOrder;  //이미지 전시순서
}
