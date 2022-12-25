package hidn.navada.oauth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OAuthJpaRepo extends JpaRepository<OAuth,Long> {
    Optional<OAuth> findByUserEmail(@Param("userEmail") String userEmail);
}
