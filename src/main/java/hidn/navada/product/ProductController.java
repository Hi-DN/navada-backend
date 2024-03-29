package hidn.navada.product;

import hidn.navada.comm.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class ProductController {
    private final ProductService productService;
    private final ResponseService responseService;

    //상품 등록
    @PostMapping(value="/user/{userId}/product")
    public SingleResponse<Product> saveProduct(@PathVariable long userId, @Valid @ModelAttribute ProductParams productParams,
                                               @RequestParam(value="file",required = false) MultipartFile file) throws IOException {
        return responseService.getSingleResponse(productService.createProduct(userId, productParams,file));
    }

    //상품 수정
    @PatchMapping(value = "/product/{productId}")
    public SingleResponse<Product> modifyProduct(@PathVariable long productId, @Valid @ModelAttribute ProductParams productParams,
                                                 @RequestPart(value="file",required = false) MultipartFile file) throws IOException {
        return responseService.getSingleResponse(productService.modifyProduct(productId, productParams,file));
    }

    //사용자별 상품 리스트 조회
    @GetMapping(value = "/user/{userId}/products")
    public PageResponse<Product> getProductsByUser(@PathVariable long userId, @RequestParam(required = false) List<Character> productExchangeStatusCds, @PageableDefault(size = 20)Pageable pageable){
        return responseService.getPageResponse(productService.getProductsByUser(userId,productExchangeStatusCds,pageable));
    }

    // 내물품 리스트 조회(교환 신청하기 화면에서 상대방에게 이미 신청했는지 체크위해 사용)
    @GetMapping(value = "user/{userId}/products/check/{theirProductId}")
    public PageResponse<ProductDto> getProductsByUserWithCheckingIfRequestedAlready(@PathVariable long userId, @PathVariable long theirProductId, @PageableDefault(size = 20) Pageable pageable) {
        return responseService.getPageResponse(productService.getProductsByUserWithCheckingIfRequestedAlready(userId, theirProductId, pageable));
    }

    //상품 단건 조회
    @GetMapping(value = "/user/{userId}/product/{productId}")
    public SingleResponse<ProductDetailDto> getProduct(@PathVariable long userId, @PathVariable long productId){
        return responseService.getSingleResponse(productService.getOneProduct(userId, productId));
    }

    //상품 검색
    @GetMapping(value = "/user/{userId}/products/search")
    public PageResponse<ProductSearchDto> searchProductsByName(@PathVariable long userId, @ModelAttribute ProductSearchOptions productSearchOptions,
                                                      @PageableDefault(sort = "product_id",direction = Sort.Direction.DESC) Pageable pageable){
        return responseService.getPageResponse(productService.searchProducts(userId, productSearchOptions, pageable));
    }

    //상품 삭제
    @DeleteMapping(value = "/product/{productId}")
    public CommonResponse deleteProduct(@PathVariable long productId){
        productService.deleteProduct(productId);
        return responseService.getSuccessResponse();
    }

    //특정 상품에 교환신청가능한 내 상품목록 조회
    @GetMapping(value = "/user/{userId}/products/request")
    public PageResponse<Product> getProductsForRequest(@PathVariable long userId,
                                                       @RequestParam long acceptorProductId,
                                                       @PageableDefault(size = 20) Pageable pageable){
        return responseService.getPageResponse(productService.getProductsForRequest(userId,acceptorProductId, pageable));
    }

}
