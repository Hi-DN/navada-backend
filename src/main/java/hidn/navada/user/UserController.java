package hidn.navada.user;

import hidn.navada.comm.config.security.token.TokenService;
import hidn.navada.comm.response.CommonResponse;
import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;
    private final ResponseService responseService;

    // 회원 가입
    @PostMapping(value = "/signup")
    public SingleResponse<UserDto> createUser(@Valid @RequestBody UserParams params) {
        return responseService.getSingleResponse(new UserDto(userService.signUp(params)));
    }

    // 닉네임 사용 가능 여부
    @GetMapping(value = "/signup/check")
    public SingleResponse<Boolean> checkNicknameUsable(@RequestParam String nickname){
        return responseService.getSingleResponse(userService.checkNicknameUsable(nickname));
    }

    // 로그아웃
    @DeleteMapping(value = "/user/{userId}/signout")
    public CommonResponse signOut(@PathVariable long userId) {
        tokenService.deleteRefreshToken(userId);
        return responseService.getSuccessResponse();
    }

    // 회원 단건 조회
    @GetMapping(value = "/user/{userId}")
    public SingleResponse<User> getUser(@PathVariable long userId){
        return responseService.getSingleResponse(userService.getOneUser(userId));
    }

    // 회원 단건 조회 by productId
    @GetMapping(value = "/user")
    public SingleResponse<User> getUserByProductId(@RequestParam long productId){
        return responseService.getSingleResponse(userService.getOneUserByProductId(productId));
    }

    // 회원정보 수정
    @PutMapping(value = "/user/{userId}")
    public SingleResponse<UserDto> modifyUser(@PathVariable long userId, @RequestBody UserUpdateParams params) {
        return responseService.getSingleResponse(new UserDto(userService.modifyUser(userId, params)));
    }

    // 알림 확인 여부 조회
    @GetMapping("/user/{userId}/noti")
    public SingleResponse<Boolean> getUserNotificationReadYn(@PathVariable long userId){
        return responseService.getSingleResponse(userService.getUserNotificationReadYn(userId));
    }

    // 알림 확인 완료
    @PatchMapping(value = "/user/{userId}/noti")
    public CommonResponse updateNotificationReadYn(@PathVariable long userId) {
        userService.updateNotificationReadYn(userId);
        return responseService.getSuccessResponse();
    }

    // 회원 탈퇴
    @DeleteMapping(value = "/user/{userId}")
    public CommonResponse deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        tokenService.deleteRefreshToken(userId);
        return responseService.getSuccessResponse();
    }
}
