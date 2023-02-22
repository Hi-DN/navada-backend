package hidn.navada.oauth.google;

import lombok.Data;

@Data
public class GoogleProfile {
    private String user_id;
    private String email;
    private boolean verified_email;
}
