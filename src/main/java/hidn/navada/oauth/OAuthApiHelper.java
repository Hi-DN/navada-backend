package hidn.navada.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
public abstract class OAuthApiHelper {

    /**
     * 소셜로그인 api 호출
     */
    protected ResponseEntity<String> getUserProfile(String accessToken, String apiUri) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> restRequest = setHeader(accessToken);
        URI uri = URI.create(apiUri);
        try {
            return restTemplate.postForEntity(uri, restRequest, String.class);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    private HttpEntity<MultiValueMap<String, String>> setHeader(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer "+ accessToken);
        return new HttpEntity<>(null, headers);
    }
}
