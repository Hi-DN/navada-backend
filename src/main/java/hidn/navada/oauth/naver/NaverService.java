package hidn.navada.oauth.naver;


import com.google.gson.Gson;
import hidn.navada.comm.exception.NaverSignInException;
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
public class NaverService extends OAuthApiHelper {

    private final Gson gson;


    /**
     * 네이버토큰으로 이메일 받아오기
     */
    public String getEmailFromNaver(String token) {
        try {
            NaverProfile naverUser = getUserProfileFromNaver(token);
            return naverUser.getEmail();
        } catch (Exception e) {
            throw new NaverSignInException();
        }
    }

    private NaverProfile getUserProfileFromNaver(String accessToken) {
        ResponseEntity<String> response = getUserProfile(accessToken, "https://openapi.naver.com/v1/nid/me");
        return gson.fromJson(response.getBody(), NaverProfile.class);
    }

}