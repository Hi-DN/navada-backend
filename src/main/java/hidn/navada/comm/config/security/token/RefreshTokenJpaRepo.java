package hidn.navada.comm.config.security.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenJpaRepo extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    boolean existsByUserId(Long userId);
    void deleteByUserId(Long userId);
}
