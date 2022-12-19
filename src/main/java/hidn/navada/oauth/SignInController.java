package hidn.navada.oauth;

import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import hidn.navada.oauth.kakao.KakaoParams;
import hidn.navada.oauth.kakao.KakaoService;
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
    private final ResponseService responseService;

    @PostMapping("/user/signin/kakao")
    public SingleResponse<SignInResponse> signInByKakaoToken(@Valid @RequestBody KakaoParams params) {
        return responseService.getSingleResponse(kakaoService.signInByKakaoToken(params));
    }
}
