package hidn.navada.notification;

import hidn.navada.comm.enums.NotificationType;
import hidn.navada.comm.exception.UserNotFoundException;
import hidn.navada.exchange.request.Request;
import hidn.navada.user.User;
import hidn.navada.user.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationJpaRepo notificationJpaRepo;
    private final UserJpaRepo userJpaRepo;

    public Notification createNotification(User receiver,NotificationType type, String content){
        receiver.setUserNotificationReadYn(false);  // 알림 확인 여부 = false

        return notificationJpaRepo.save(
                Notification.builder()
                .notificationContent(content)
                .notificationType(type)
                .receiver(receiver)
                .build()
        );
    }

    // 유저별 알림 목록 조회
    public List<Notification> getNotificationsByReceiver(long userId){
        User user=userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        return notificationJpaRepo.findNotificationsByReceiver(user);
    }

    public String getAcceptedNotiContent(Request request){
        String acceptorProductName=request.getAcceptorProduct().getProductName();
        String requesterProductName=request.getRequesterProduct().getProductName();

        return String.format("'%s'에 대한 '%s'의 요청이 수락되었습니다.",acceptorProductName,requesterProductName);
    }

    public String getDeniedNotiContent(Request request){
        String acceptorProductName=request.getAcceptorProduct().getProductName();
        String requesterProductName=request.getRequesterProduct().getProductName();

        return String.format("'%s'에 대한 '%s'의 요청이 거절되었습니다.",acceptorProductName,requesterProductName);
    }

    public String getCompleteNotiContent(String userName,Request request){
        String acceptorProductName=request.getAcceptorProduct().getProductName();
        String requesterProductName=request.getRequesterProduct().getProductName();

        return String.format("%s님이 '%s'와 '%s'에 대한 교환을 완료했습니다. 교환 완료 후, 평점을 남겨보세요!",userName,acceptorProductName,requesterProductName);
    }

    public String getPeriodicCompleteNotiContent(Request request){
        String acceptorProductName=request.getAcceptorProduct().getProductName();
        String requesterProductName=request.getRequesterProduct().getProductName();

        return String.format("'%s'와 '%s'에 대한 교환을 완료하셨습니까? 교환 완료 후, 평점을 남겨보세요!",acceptorProductName,requesterProductName);
    }

    public String getProductDeletionNotiContent(String deletedProductName){
        return String.format("'%s'에 대한 교환 요청이 해당 물품의 삭제로 인해 자동 취소되었습니다.",deletedProductName);
    }
}
