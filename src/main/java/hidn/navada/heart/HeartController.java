package hidn.navada.heart;

import hidn.navada.comm.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class HeartController {
    private final ResponseService responseService;
    private final HeartService heartService;

    //좋아요 등록
    @PostMapping(value = "/product/{productId}/heart")
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
    @GetMapping(value = "/user/{userId}/hearts")
    public PageResponse<Heart> getHeartsByUser(@PathVariable long userId, @RequestParam boolean showAll, @PageableDefault(size = 20) Pageable pageable){
        return responseService.getPageResponse(heartService.getHeartsByUser(userId, showAll, pageable));
    }
}
