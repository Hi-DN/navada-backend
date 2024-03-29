package hidn.navada.comm.config.security.token;

import hidn.navada.comm.config.security.JwtTokenProvider;
import hidn.navada.comm.exception.AccessDeniedException;
import hidn.navada.comm.exception.RefreshTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenJpaRepo refreshTokenJpaRepo;

    public TokenDto signIn(Long userId) {
        TokenDto newToken = jwtTokenProvider.createToken(userId);

        if(refreshTokenJpaRepo.existsByUserId(userId)) {
            //기존 존재하던 refresh token 있다면 삭제
            refreshTokenJpaRepo.deleteByUserId(userId);
        }

        RefreshToken newRefreshToken = RefreshToken.builder().refreshToken(newToken.getRefreshToken()).userId(newToken.getUserId()).build();
        String newAccessToken = recreateAccessToken(newRefreshToken.getRefreshToken());
        refreshTokenJpaRepo.save(newRefreshToken);

        return new TokenDto(userId, newAccessToken, newRefreshToken.getRefreshToken());
    }

    public boolean validateRefreshToken(String refreshToken) {
        try{
            RefreshToken findToken = findRefreshToken(refreshToken);
            return jwtTokenProvider.validateRefreshToken(findToken);
        } catch (RefreshTokenNotFoundException e) {
            return false;
        }
    }

    public String recreateAccessToken(String refreshToken) {
        return jwtTokenProvider.recreateAccessToken(refreshToken);

    }

    public RefreshToken findRefreshToken(String refreshToken) {
        return refreshTokenJpaRepo.findByRefreshToken(refreshToken).orElseThrow(RefreshTokenNotFoundException::new);
    }

    public Long getUserIdFromAccessToken(String accessToken) {
        return Long.parseLong(jwtTokenProvider.getUserPk(accessToken));
    }

    public void deleteRefreshToken(Long userId) {
        refreshTokenJpaRepo.deleteByUserId(userId);
    }

}
