package hidn.navada.oauth;

import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import hidn.navada.oauth.kakao.KakaoParams;
import hidn.navada.oauth.kakao.KakaoService;
import hidn.navada.user.UserDto;
import hidn.navada.user.UserParams;
import hidn.navada.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignInController {
    private final KakaoService kakaoService;
    private final OAuthService oauthService;
    private final UserService userService;
    private final ResponseService responseService;

    // 회원 가입
    @PostMapping(value = "/signup")
    public SingleResponse<UserDto> createUser(@Valid @RequestBody UserParams params) {
        return responseService.getSingleResponse(new UserDto(userService.createUser(params)));
    }

    // 카카오 로그인
    @PostMapping("/signin/kakao")
    public SingleResponse<SignInResponse> signInByKakaoToken(@Valid @RequestBody KakaoParams params) {
        return responseService.getSingleResponse(kakaoService.signInByKakaoToken(params));
    }

    // 구글, 네이버 로그인
    @PostMapping("/signin/oauth")
    public SingleResponse<SignInResponse> signInByOAuth(@Valid @RequestBody OAuthParams params) {
        return responseService.getSingleResponse(oauthService.signInByOAuth(params));
    }
}
