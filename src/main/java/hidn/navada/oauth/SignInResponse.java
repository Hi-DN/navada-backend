package hidn.navada.oauth;

import hidn.navada.user.User;
import hidn.navada.user.UserDto;
import lombok.Data;


@Data
public class SignInResponse {
    private boolean isSignUp;
    private String accessToken;
    private String refreshToken;
    private UserDto user;

    public SignInResponse(User user, String accessToken, String refreshToken, boolean isSignUp) {
        this.isSignUp = isSignUp;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = new UserDto(user);
    }
}
