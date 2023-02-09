package hidn.navada.oauth;

import hidn.navada.comm.config.security.token.TokenDto;
import hidn.navada.comm.config.security.token.TokenService;
import hidn.navada.comm.exception.OAuthNotFoundException;
import hidn.navada.oauth.google.GoogleService;
import hidn.navada.oauth.kakao.KakaoService;
import hidn.navada.oauth.naver.NaverService;
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
    private final TokenService tokenService;
    private final KakaoService kakaoService;
    private final GoogleService googleService;
    private final NaverService naverService;

    /**
     * OAuth 로그인
     */
    public SignInResponse signInByOAuth(OAuthParams params) {
        SignInPlatform platform = SignInPlatform.valueOf(params.getSignInPlatform());
        String email = getSocialEmail(params.getAccessToken(), platform);
        OAuthDto oauthDto = new OAuthDto(email, platform);

        try {
            //로그인
            OAuth oauth = checkUserExist(email, platform);
            return makeResponse(oauth.getUser(), oauthDto);
        } catch (OAuthNotFoundException e) {
            //회원가입 진행
            return makeResponse(null, oauthDto);
        }
    }

    private String getSocialEmail(String accessToken, SignInPlatform platform) {
        switch (platform) {
            case KAKAO:
                return kakaoService.getEmailFromKakao(accessToken);
            case GOOGLE:
                return googleService.getEmailFromGoogle(accessToken);
            default://NAVER
                return naverService.getEmailFromNaver(accessToken);
        }
    }

    private OAuth checkUserExist(String email, SignInPlatform platform) {
        return oauthJpaRepo.findByUserEmailAndPlatform(email, platform).orElseThrow(OAuthNotFoundException::new);
    }

    private SignInResponse makeResponse(User user, OAuthDto oauth) {
        if(user != null) {
            //로그인
            TokenDto token = tokenService.signIn(user.getUserId());

            return new SignInResponse(
                    new UserDto(user),
                    oauth,
                    token.getAccessToken(),
                    token.getRefreshToken());
        } else {
            //회원가입 진행
            return new SignInResponse(null, oauth, null, null);
        }
    }

}
