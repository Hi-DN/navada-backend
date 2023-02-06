package hidn.navada.oauth.google;

import com.google.gson.Gson;
import hidn.navada.comm.exception.GoogleSignInException;
import hidn.navada.oauth.OAuthApiHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GoogleService extends OAuthApiHelper {

    private final Gson gson;


    /**
     * 구글토큰으로 이메일 받아오기
     */
    public String getEmailFromGoogle(String token) {
        try {
            GoogleProfile googleUser = getUserProfileFromGoogle(token);
            return googleUser.getEmail();
        } catch (Exception e) {
            throw new GoogleSignInException();
        }
    }

    private GoogleProfile getUserProfileFromGoogle(String accessToken) {
        ResponseEntity<String> response = getUserProfile(accessToken, "https://www.googleapis.com/oauth2/v1/tokeninfo");
        return gson.fromJson(response.getBody(), GoogleProfile.class);
    }

}
