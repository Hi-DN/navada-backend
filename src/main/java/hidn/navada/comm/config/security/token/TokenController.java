package hidn.navada.comm.config.security.token;

import hidn.navada.comm.exception.RefreshTokenInvalidException;
import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class TokenController {

    private final TokenService tokenService;
    private final ResponseService responseService;

    @PostMapping("/refresh")
    public SingleResponse<TokenDto> validateRefreshToken(@RequestBody TokenParams params) {
        String refreshToken = params.getRefreshToken();

        boolean isValidRefreshToken = tokenService.validateRefreshToken(refreshToken);
        if(isValidRefreshToken) {
            String accessToken = tokenService.recreateAccessToken(refreshToken);
            Long userId = tokenService.getUserIdFromAccessToken(accessToken);
            return responseService.getSingleResponse(new TokenDto(userId, accessToken, refreshToken));
        } else {
            throw new RefreshTokenInvalidException();
        }
    }
}
