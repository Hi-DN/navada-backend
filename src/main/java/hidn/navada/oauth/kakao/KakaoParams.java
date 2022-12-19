package hidn.navada.oauth.kakao;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class KakaoParams {
    @NotNull(message = "Kakao Access Token이 비어있습니다.")
    private String accessToken;
}
