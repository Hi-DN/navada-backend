package hidn.navada.user;

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
    private final ResponseService responseService;

    // 회원 가입
    @PostMapping(value = "/signup")
    public SingleResponse<UserDto> createUser(@Valid @RequestBody UserParams params) {
        return responseService.getSingleResponse(new UserDto(userService.signUp(params)));
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

    // 회원 탈퇴
    @DeleteMapping(value = "/user/{userId}")
    public CommonResponse deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return responseService.getSuccessResponse();
    }
}
