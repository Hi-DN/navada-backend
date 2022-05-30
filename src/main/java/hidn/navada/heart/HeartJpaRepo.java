package hidn.navada.heart;

import hidn.navada.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartJpaRepo extends JpaRepository<Heart,Long> {
    List<Heart> findHeartsByUser(User user);
}
