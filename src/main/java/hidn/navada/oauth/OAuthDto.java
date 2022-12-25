package hidn.navada.oauth;

import lombok.Data;

@Data
public class OAuthDto {
    private String userEmail;
    private SigninPlatform signinPlatform;

    public OAuthDto(String userEmail, SigninPlatform platform) {
        this.userEmail = userEmail;
        this.signinPlatform = platform;
    }
}
