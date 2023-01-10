package hidn.navada.oauth;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OAuthParams {
    @NotNull(message = "회원 이메일은 필수값입니다.")
    private String userEmail;
    private String userNickname;
    @NotNull(message = "플랫폼은 필수값입니다.")
    private String signInPlatform;
}
