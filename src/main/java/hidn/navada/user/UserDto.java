package hidn.navada.user;

import hidn.navada.comm.enums.UserLevel;
import lombok.Data;


@Data
public class UserDto {
    private long userId;
    private String userName;
    private String userNickname;
    private String userPhoneNum;
    private String userAddress;
    private UserLevel userLevel;
    private float userRating;
    private int userTradeCount;
    private int userRatingCount;

    public UserDto(User user){
        userId = user.getUserId();
        userName = user.getUserRealName();
        userNickname = user.getUserNickname();
        userPhoneNum = user.getUserPhoneNum();
        userAddress = user.getUserAddress();
        userLevel = user.getUserLevel();
        userRating = user.getUserRating();
        userTradeCount = user.getUserTradeCount();
        userRatingCount = user.getUserRatingCount();
    }
}
