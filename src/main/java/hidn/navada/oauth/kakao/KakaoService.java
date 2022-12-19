package hidn.navada.oauth.kakao;

import com.google.gson.Gson;
import hidn.navada.comm.exception.KaKaoSignInException;
import hidn.navada.comm.exception.UserNotFoundException;
import hidn.navada.oauth.SignInResponse;
import hidn.navada.user.User;
import hidn.navada.user.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class KakaoService {
    private final UserJpaRepo userJpaRepo;
    private final Gson gson;

    /**
     * 카카오토큰으로 로그인 및 회원가입
     */
    public SignInResponse signInByKakaoToken(KakaoParams params) {
        KakaoProfile userProfile;

        try {
            userProfile = getUserProfileFromKakao(params.getAccessToken());
        } catch (Exception e) {
            throw new KaKaoSignInException();
        }

        try {
            User user = findUser(userProfile.getEmail(), userProfile.getNickName());
            return makeResponse(user, false);
        } catch (UserNotFoundException e) {
            User newUser = createUser(userProfile.getEmail(), userProfile.getNickName());
            return makeResponse(newUser, true);
        }
    }

    private KakaoProfile getUserProfileFromKakao(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> restRequest = setHeader(accessToken);
        URI uri = URI.create("https://kapi.kakao.com/v2/user/me");
        ResponseEntity<String> response = restTemplate.postForEntity(uri, restRequest, String.class); //예외처리 추가 필요?
        return gson.fromJson(response.getBody(), KakaoProfile.class);
    }

    private HttpEntity<MultiValueMap<String, String>> setHeader(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer "+ accessToken);
        return new HttpEntity<>(null, headers);
    }

    private User findUser(String email, String nickName) {
        return userJpaRepo.findByUserEmailAndUserNickname(email, nickName).orElseThrow(UserNotFoundException::new);
    }

    private User createUser(String email, String nickName) {
        return userJpaRepo.save(User.create(email, nickName));
    }

    private SignInResponse makeResponse(User user, boolean isSignUp) {
        return new SignInResponse(user, "accessToken", "refreshToken", isSignUp);
    }
}
