package hidn.navada.user;

import lombok.Data;

@Data
public class UserUpdateParams {

    private String userNickname;    // 회원 별명

    private String userPhoneNum;    // 회원 전화번호

    private String userAddress;     // 회원 주소

}