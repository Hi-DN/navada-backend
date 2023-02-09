package hidn.navada.oauth.kakao;


import com.google.gson.Gson;
import hidn.navada.comm.exception.KakaoSignInException;
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
public class KakaoService extends OAuthApiHelper {

    private final Gson gson;


    /**
     * 카카오토큰으로 이메일 받아오기
     */
    public String getEmailFromKakao(String token) {
        try {
            KakaoProfile kakaoUser = getUserProfileFromKakao(token);
            return kakaoUser.getEmail();
        } catch (Exception e) {
            throw new KakaoSignInException();
        }
    }

    private KakaoProfile getUserProfileFromKakao(String accessToken) {
        ResponseEntity<String> response = getUserProfile(accessToken, "https://kapi.kakao.com/v2/user/me");
        return gson.fromJson(response.getBody(), KakaoProfile.class);
    }

}