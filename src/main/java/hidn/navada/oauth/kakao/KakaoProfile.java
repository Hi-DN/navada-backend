package hidn.navada.oauth.kakao;


import lombok.Data;

@Data
public class KakaoProfile {
    private Long id;
    private String connected_at;
    private KakaoAccount kakao_account;

    @Data
    public static class KakaoAccount {
        private Boolean email_needs_agreement;
        private Boolean has_email;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String email;
    }

    public String getEmail(){
        return this.kakao_account.email;
    }

}