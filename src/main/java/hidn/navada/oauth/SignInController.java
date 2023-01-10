package hidn.navada.oauth;

import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignInController {
    private final OAuthService oauthService;
    private final ResponseService responseService;

    // 소셜 로그인 (카카오, 구글, 네이버)
    @PostMapping("/signin/oauth")
    public SingleResponse<SignInResponse> signInByOAuth(@Valid @RequestBody OAuthParams params) {
        return responseService.getSingleResponse(oauthService.signInByOAuth(params));
    }
}
