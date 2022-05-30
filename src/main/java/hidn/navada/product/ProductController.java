package hidn.navada.product;

import hidn.navada.comm.response.CommonResponse;
import hidn.navada.comm.response.ListResponse;
import hidn.navada.comm.response.ResponseService;
import hidn.navada.comm.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ResponseService responseService;

    //상품 등록
    @PostMapping(value="/product/{userId}")
    public SingleResponse<Product> saveProduct(@PathVariable long userId, @ModelAttribute ProductParams productParams){
        return responseService.getSingleResponse(productService.createProduct(userId, productParams));
    }

    //상품 수정
    @PatchMapping(value = "/product/{productId}")
    public SingleResponse<Product> modifyProduct(@PathVariable long productId, @ModelAttribute ProductParams productParams){
        return responseService.getSingleResponse(productService.modifyProduct(productId, productParams));
    }

    //사용자별 상품 리스트 조회
    @GetMapping(value = "/products/{userId}")
    public ListResponse<Product> getProductsByUser(@PathVariable long userId){
        return responseService.getListResponse(productService.getProductsByUser(userId));
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
