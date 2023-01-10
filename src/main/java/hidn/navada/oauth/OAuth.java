package hidn.navada.oauth;

import hidn.navada.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oauthId;           // pk

    @Column(nullable = false)
    private String userEmail;       // 회원 이메일

    @Column(nullable = false, length=6) @Enumerated(EnumType.STRING)
    private SignInPlatform platform;      // 회원 가입 플랫폼

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;              // 회원 (fk)


    //==생성 메서드==//
    public static OAuth create(User user, String userEmail, SignInPlatform platform) {
        OAuth oauth = new OAuth();
        oauth.user = user;
        oauth.userEmail = userEmail;
        oauth.platform = platform;
        return oauth;
    }
}
