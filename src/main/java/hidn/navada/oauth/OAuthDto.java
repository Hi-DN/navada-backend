package hidn.navada.oauth;

import lombok.Data;

@Data
public class OAuthDto {
    private String userEmail;
    private SignInPlatform signInPlatform;

    public OAuthDto(String userEmail, SignInPlatform platform) {
        this.userEmail = userEmail;
        this.signInPlatform = platform;
    }
}
