package hidn.navada.user;

import hidn.navada.comm.enums.UserLevel;
import lombok.Data;


@Data
public class UserDto {
    private long userId;
    private String userName;
    private String userNickname;
    private String userEmail;
    private String userPhoneNum;
    private String userAddress;
    private UserLevel userLevel;

    public UserDto(User user){
        userId = user.getUserId();
        userName = user.getUserName();
        userNickname = user.getUserNickname();
        userEmail = user.getUserEmail();
        userPhoneNum = user.getUserPhoneNum();
        userAddress = user.getUserAddress();
        userLevel = user.getUserLevel();
    }
}
