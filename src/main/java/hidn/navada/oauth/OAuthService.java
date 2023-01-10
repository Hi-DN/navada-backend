package hidn.navada.oauth;

import hidn.navada.comm.exception.OAuthNotFoundException;
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
        SignInPlatform platform = SignInPlatform.valueOf(params.getSignInPlatform());

        try {
            OAuth oauth = checkUserExist(params.getUserEmail(), platform);
            return makeResponse(oauth);
        } catch (OAuthNotFoundException e) {
            return makeResponse(null);
        }
    }

    private OAuth checkUserExist(String email, SignInPlatform platform) {
        return oauthJpaRepo.findByUserEmailAndPlatform(email, platform).orElseThrow(OAuthNotFoundException::new);
    }

    private SignInResponse makeResponse(OAuth oauth) {
        return (oauth != null)
                ? new SignInResponse(
                        new UserDto(oauth.getUser()),
                        new OAuthDto(oauth.getUserEmail(), oauth.getPlatform()),
                        "accessToken",
                        "refreshToken")
                : new SignInResponse(null, null, null, null);
    }
}