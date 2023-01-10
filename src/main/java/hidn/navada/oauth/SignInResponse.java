package hidn.navada.oauth;

import hidn.navada.user.UserDto;
import lombok.Data;


@Data
public class SignInResponse {
    private String accessToken;
    private String refreshToken;
    private UserDto user;
    private OAuthDto oauth;

    public SignInResponse(UserDto user, OAuthDto oauth, String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
        this.oauth = oauth;
    }
}
