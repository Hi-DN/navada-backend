package hidn.navada.notification;

import hidn.navada.comm.enums.NotificationType;
import hidn.navada.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationJpaRepo notificationJpaRepo;

    public Notification createNotification(User receiver,NotificationType type, String content){
        return notificationJpaRepo.save(
                Notification.builder()
                .notificationContent(content)
                .notificationType(type)
                .receiver(receiver)
                .build()
        );
    }

    // 유저별 알림 목록 조회
    public List<Notification> getNotificationsByUser(long userId){
        return notificationJpaRepo.findNotificationsByUser(userId);
    }

    public String getAcceptedNotiContent(String acceptorProductName, String requesterProductName){
        return String.format("'%s'에 대한 '%s'의 요청이 수락되었습니다.",acceptorProductName,requesterProductName);
    }

    public String getDeniedNotiContent(String acceptorProductName, String requesterProductName){
        return String.format("'%s'에 대한 '%s'의 요청이 거절되었습니다.",acceptorProductName,requesterProductName);
    }

    public String getCompleteNotiContent(String userName, String acceptorProductName, String requesterProductName){
        return String.format("%s님이 '%s'와 '%s'에 대한 교환을 완료했습니다. 교환 완료 후, 평점을 남겨보세요!",userName,acceptorProductName,requesterProductName);
    }

    public String getPeriodicCompleteNotiContent(String acceptorProductName, String requesterProductName){
        return String.format("'%s'와 '%s'에 대한 교환을 완료하셨습니까? 교환 완료 후, 평점을 남겨보세요!",acceptorProductName,requesterProductName);
    }

    public String getProductDeletionNotiContent(String deletedProductName){
        return String.format("'%s'에 대한 교환 요청이 해당 물품의 삭제로 인해 자동 취소되었습니다.",deletedProductName);
    }
}
