package hidn.navada.user;

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
    public SingleResponse<UserDto> modifyUser(@PathVariable long userId, @Valid @RequestBody UserParams params) {
        return responseService.getSingleResponse(new UserDto(userService.modifyUser(userId, params)));
    }
}
