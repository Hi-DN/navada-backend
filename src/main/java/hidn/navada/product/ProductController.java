package hidn.navada.product;

import hidn.navada.comm.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class ProductController {
    private final ProductService productService;
    private final ResponseService responseService;

    //상품 등록
    @PostMapping(value="/user/{userId}/product")
    public SingleResponse<Product> saveProduct(@PathVariable long userId, @ModelAttribute ProductParams productParams){
        return responseService.getSingleResponse(productService.createProduct(userId, productParams));
    }

    //상품 수정
    @PatchMapping(value = "/product/{productId}")
    public SingleResponse<Product> modifyProduct(@PathVariable long productId, @ModelAttribute ProductParams productParams){
        return responseService.getSingleResponse(productService.modifyProduct(productId, productParams));
    }

    //사용자별 상품 리스트 조회
    @GetMapping(value = "/user/{userId}/products")
    public PageResponse<Product> getProductsByUser(@PathVariable long userId, @PageableDefault(size = 20)Pageable pageable){
        return responseService.getPageResponse(productService.getProductsByUser(userId,pageable));
    }


    //상품 단건 조회
    @GetMapping(value = "/product/{productId}")
    public SingleResponse<Product> getProduct(@PathVariable long productId){
        return responseService.getSingleResponse(productService.getOneProduct(productId));
    }

    //상품 삭제
    @DeleteMapping(value = "/product/{productId}")
    public CommonResponse deleteProduct(@PathVariable long productId){
        productService.deleteProduct(productId);
        return responseService.getSuccessResponse();
    }
}
