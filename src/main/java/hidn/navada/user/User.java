package hidn.navada.user;

import hidn.navada.comm.BaseTime;
import hidn.navada.comm.enums.UserLevel;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;            // pk

    @Column(length = 64)
    private String userRealName;        // 회원 이름

    @Column(length = 10)
    private String userNickname;    // 회원 별명

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
    public static User create(UserParams params) {
        User user = new User();
        user.userRealName = params.getUserName();
        user.userNickname = params.getUserNickname();
        user.userPhoneNum = params.getUserPhoneNum();
        user.userAddress = params.getUserAddress();
        return user;
    }

    //==수정 메서드==//
    public void update(UserUpdateParams params) {
        userNickname = params.getUserNickname();
        userPhoneNum = params.getUserPhoneNum();
        userAddress = params.getUserAddress();
    }

    //==Sprint Security UserDetails==//
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userId.toString();  //유저 식별값 리턴
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
