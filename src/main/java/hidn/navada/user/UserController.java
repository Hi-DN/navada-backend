package hidn.navada.user;

import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
