package hidn.navada.oauth.kakao;

import lombok.Data;

@Data
public class KakaoProfile {
    private Long id;
    private String connected_at;
    private KakaoAccount kakao_account;

    @Data
    public static class KakaoAccount {
        private Boolean profile_nickname_needs_agreement;
        private Profile profile;
        private Boolean has_email;
        private Boolean email_needs_agreement;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String email;
    }

    @Data
    public static class Profile {
        private String nickname;
    }

    public String getEmail(){
        return this.kakao_account.email;
    }

    public String getNickName(){
        return this.kakao_account.profile.nickname;
    }
}
