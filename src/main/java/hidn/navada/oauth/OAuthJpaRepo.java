package hidn.navada.oauth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthJpaRepo extends JpaRepository<OAuth,Long> {
    Optional<OAuth> findByUserEmailAndPlatform(String userEmail, SignInPlatform platform);
}
