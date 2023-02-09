package hidn.navada.oauth;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OAuthParams {

    @NotNull(message = "소셜 access token은 필수값입니다.")
    private String accessToken;

    @NotNull(message = "플랫폼은 필수값입니다.")
    private String signInPlatform;

}
