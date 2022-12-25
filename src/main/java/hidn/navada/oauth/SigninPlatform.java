package hidn.navada.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SigninPlatform {
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE"),
    NAVER("NAVER");
    private final String value;
}