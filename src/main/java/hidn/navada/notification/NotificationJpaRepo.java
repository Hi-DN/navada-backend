package hidn.navada.notification;

import hidn.navada.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationJpaRepo extends JpaRepository<Notification,Long> {
    List<Notification> findNotificationsByReceiverOrderByCreatedDateDesc(User user);
}
