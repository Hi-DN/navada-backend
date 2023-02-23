package hidn.navada.comm.config.security;

import hidn.navada.comm.config.security.token.RefreshToken;
import hidn.navada.comm.config.security.token.TokenDto;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String accessSecretKey = "navadaprojectsecretsecretkey";
    private String refreshSecretKey = "navadaprojectrefreshsecretsecretkey";

    //    private final long accessTokenValidTime = 5 * 1000L;  //Access Token 유효시간 5초 (테스트용)
    private final long accessTokenValidTime = 30 * 60 * 1000L;  //Access Token 유효시간 30분
    private final long refreshTokenValidTime = 14 * 24 * 60 * 60 * 1000L; //Refresh Token 유효시간 14일

    private final UserDetailsService userDetailsService;

    //객체 초기화, 시크릿 키를 Base64 인코딩
    @PostConstruct
    protected void init() {
        accessSecretKey = Base64.getEncoder().encodeToString(accessSecretKey.getBytes());
        refreshSecretKey = Base64.getEncoder().encodeToString(refreshSecretKey.getBytes());
    }

    //JWT 토큰 생성
    public TokenDto createToken(Long userPk) {
        Claims claims = Jwts.claims().setSubject(userPk.toString());   //JWT payload 부분에 저장되는 정보 단위.(보통 user 식별 값 넣음)
        Date now = new Date();

        //Access Token
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)
                .compact();

        //Refresh Token
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)
                .compact();

        return TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).userId(userPk).build();
    }

    //JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰에서 회원정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //리퀘스트의 헤더에서 토큰값을 가져온다. "Authorization" : "TOKEN 값"
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    //Access Token 유효성, 만료일자 확인
    public boolean validateAccessToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    //Refresh Token 유효성, 만료일자 확인
    public boolean validateRefreshToken(RefreshToken refreshTokenObj) {
        String refreshToken = refreshTokenObj.getRefreshToken();

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(refreshToken);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            //refresh 토큰 만료시, 로그인 필요
            return false;
        }
    }

    public String recreateAccessToken(String refreshToken) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(refreshToken);
        return createAccessToken(claims.getBody().get("sub").toString());
    }

    private String createAccessToken(String userPk) {
        Claims claims = Jwts.claims().setSubject(userPk);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)
                .compact();
    }
}