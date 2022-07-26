package hidn.navada.heart;

import hidn.navada.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartJpaRepo extends JpaRepository<Heart,Long> {
    Page<Heart> findHeartsByUser(User user, Pageable pageable);
}
