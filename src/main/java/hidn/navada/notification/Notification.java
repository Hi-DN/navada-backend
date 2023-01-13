package hidn.navada.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hidn.navada.comm.BaseTime;
import hidn.navada.comm.enums.NotificationType;
import hidn.navada.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String notificationContent;

    @JsonIgnore
    @JoinColumn(referencedColumnName = "userId") @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User receiver;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

}
