package hidn.navada.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserParams {

    @Size(min = 1, max = 64, message = "회원 이름은 1자 이상 64자 이하여야 합니다.")
    private String userName;        // 회원 이름

    @Size(min = 1, max = 64, message = "회원 별명은 1자 이상 10자 이하여야 합니다.")
    private String userNickname;    // 회원 별명

    private String userEmail;       // 회원 이메일

    @NotNull(message = "전화번호는 필수값입니다.")
    private String userPhoneNum;    // 회원 전화번호

//    @NotNull(message = "전화번호는 필수값입니다.")
    private String userAddress;    // 회원 전화번호

}
