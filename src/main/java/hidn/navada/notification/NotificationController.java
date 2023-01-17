package hidn.navada.notification;

import hidn.navada.comm.response.ListResponse;
import hidn.navada.comm.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class NotificationController {
    private final ResponseService responseService;
    private final NotificationService notificationService;

    @GetMapping("/user/{userId}/notifications")
    public ListResponse<Notification> getNotificationsByUser(@PathVariable long userId){
        return responseService.getListResponse(notificationService.getNotificationsByReceiver(userId));
    }
}
