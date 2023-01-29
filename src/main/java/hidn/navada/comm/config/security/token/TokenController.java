package hidn.navada.comm.config.security.token;

import hidn.navada.comm.exception.RefreshTokenInvalidException;
import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;
    private final ResponseService responseService;

    @PostMapping("/user/{userId}/refresh")
    public SingleResponse<TokenDto> validateRefreshToken(@PathVariable Long userId, @RequestBody TokenParams params) {
        String refreshToken = params.getRefreshToken();

        boolean isValidRefreshToken = tokenService.validateRefreshToken(userId, refreshToken);
        if(isValidRefreshToken) {
            String accessToken = tokenService.recreateAccessToken(refreshToken);
            return responseService.getSingleResponse(new TokenDto(userId, accessToken, refreshToken));
        } else {
            throw new RefreshTokenInvalidException();
        }
    }
}
