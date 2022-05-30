package hidn.navada.user;

import hidn.navada.comm.BaseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;            // pk

    private String userName;        // 회원 이름

    private String userNickname;    // 회원 별명

    private String userEmail;       // 회원 이메일

    private String userPassword;    // 회원 비밀번호

    private String userPhoneNum;    // 회원 전화번호

    @Column(nullable = false)
    private String userAddress;     // 회원 주소(동네)

    @Builder.Default
    private int userLevel=0;        // 0:일반, 1:골드, 2:VIP
}
