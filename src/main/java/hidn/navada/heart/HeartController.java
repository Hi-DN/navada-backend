package hidn.navada.heart;

import hidn.navada.comm.response.CommonResponse;
import hidn.navada.comm.response.ListResponse;
import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class HeartController {
    private final ResponseService responseService;
    private final HeartService heartService;

    //좋아요 등록
    @PostMapping(value = "/heart/{productId}")
    public SingleResponse<Heart> saveHeart(@PathVariable long productId, @RequestParam long userId){
        return responseService.getSingleResponse(heartService.saveHeart(productId, userId));
    }

    //좋아요 취소
    @DeleteMapping(value = "/heart/{heartId}")
    public CommonResponse deleteHeart(@PathVariable long heartId){
        heartService.cancelHeart(heartId);
        return responseService.getSuccessResponse();
    }

    //사용자별 좋아요 목록 조회
    @GetMapping(value = "/hearts/{userId}")
    public ListResponse<Heart> getHeartsByUser(@PathVariable long userId){
        return responseService.getListResponse(heartService.getHeartsByUser(userId));
    }
}
