package hidn.navada.oauth.naver;

import lombok.Data;

@Data
public class NaverProfile {
    private String resultcode;
    private String message;
    private NaverResponse response;

    @Data
    public static class NaverResponse {
        private String id;
        private String email;
    }

    public String getEmail(){
        return this.response.email;
    }

}