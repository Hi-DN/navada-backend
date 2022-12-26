package hidn.navada.oauth;

import hidn.navada.comm.exception.OAuthNotFoundException;
import hidn.navada.user.User;
import hidn.navada.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OAuthService {
    private final OAuthJpaRepo oauthJpaRepo;

    /**
     * OAuth 로그인
     */
    public SignInResponse signInByOAuth(OAuthParams params) {
        String email = params.getUserEmail();
        SignInPlatform platform = SignInPlatform.valueOf(params.getSignInPlatform());
        try {
            OAuth oauth = checkUserExist(params.getUserEmail());
            return makeResponse(oauth.getUser(), email, platform);
        } catch (OAuthNotFoundException e) {
            return makeResponse(null, email, platform);
        }
    }

    private OAuth checkUserExist(String email) {
        return oauthJpaRepo.findByUserEmail(email).orElseThrow(OAuthNotFoundException::new);
    }

    private SignInResponse makeResponse(User user, String userEmail, SignInPlatform platform) {
        return new SignInResponse(
                (user != null) ? new UserDto(user) : null,
                new OAuthDto(userEmail, platform),
                "accessToken",
                "refreshToken");
    }
}
