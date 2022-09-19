package hidn.navada.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import hidn.navada.comm.BaseTime;
import hidn.navada.comm.enums.UserLevel;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;            // pk

    @Column(length = 64)
    private String userName;        // 회원 이름

    @Column(length = 10)
    private String userNickname;    // 회원 별명

    private String userEmail;       // 회원 이메일

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPassword;    // 회원 비밀번호

    @Column(length = 16)
    private String userPhoneNum;    // 회원 전화번호

    @Column(nullable = false)
    private String userAddress;     // 회원 주소(동네)

    @Enumerated(EnumType.STRING)
    @Column(length = 12) @Builder.Default
    private UserLevel userLevel = UserLevel.LV1_OUTSIDER;        // lv1: 외지인, lv2: 주민, lv3: 토박이, lv4: 촌장

    @Builder.Default @ColumnDefault("0")
    private float userRating=0;       //회원 평점

    @Builder.Default @ColumnDefault("0")
    private int userTradeCount=0;     //회원 거래 횟수

    @Builder.Default @ColumnDefault("0")
    private int userRatingCount=0;    //평점 받은 횟수
}
