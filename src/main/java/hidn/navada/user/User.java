package hidn.navada.user;

import hidn.navada.comm.BaseTime;
import hidn.navada.comm.enums.UserLevel;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;            // pk

    @Column(length = 64)
    private String userName;        // 회원 이름

    @Column(length = 10)
    private String userNickname;    // 회원 별명

    @Column(nullable = false)
    private String userEmail;       // 회원 이메일

    @Column(length = 16)
    private String userPhoneNum;    // 회원 전화번호

    private String userAddress;     // 회원 주소(동네)

    @Enumerated(EnumType.STRING)
    @Column(length = 12)
    private UserLevel userLevel = UserLevel.LV1_OUTSIDER;        // lv1: 외지인, lv2: 주민, lv3: 토박이, lv4: 촌장

    @ColumnDefault("0")
    private float userRating=0;       //회원 평점

    @ColumnDefault("0")
    private int userTradeCount=0;     //회원 거래 횟수

    @ColumnDefault("0")
    private int userRatingCount=0;    //평점 받은 횟수

    //==생성 메서드==//
    public static User create(String userEmail, String userNickname) {
        User user = new User();
//        user.userName = params.getUserName();
        user.userEmail = userEmail;
//        user.userPhoneNum = params.getUserPhoneNum();
        user.userNickname = userNickname;
        return user;
    }

    //==수정 메서드==//
    public void update(UserParams params) {
        userName = params.getUserName();
        userNickname = params.getUserNickname();
        userAddress = params.getUserAddress();
        userPhoneNum = params.getUserPhoneNum();
    }
}
