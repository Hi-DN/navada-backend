package hidn.navada.product;

import hidn.navada.comm.enums.NotificationType;
import hidn.navada.comm.exception.CategoryNotFoundException;
import hidn.navada.comm.exception.ProductNotFoundException;
import hidn.navada.comm.exception.UserNotFoundException;

import hidn.navada.exchange.Exchange;
import hidn.navada.exchange.ExchangeJpaRepo;
import hidn.navada.exchange.request.Request;
import hidn.navada.exchange.request.RequestJpaRepo;
import hidn.navada.heart.HeartJpaRepo;
import hidn.navada.notification.NotificationService;
import hidn.navada.product.category.Category;
import hidn.navada.product.category.CategoryJpaRepo;
import hidn.navada.product.gcp.GCPService;
import hidn.navada.user.User;
import hidn.navada.user.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductJpaRepo productJpaRepo;
    private final CategoryJpaRepo categoryJpaRepo;
    private final UserJpaRepo userJpaRepo;
    private final HeartJpaRepo heartJpaRepo;
    private final ExchangeJpaRepo exchangeJpaRepo;
    private final RequestJpaRepo requestJpaRepo;
    private final NotificationService notificationService;
    private final GCPService gcpService;

    //상품 등록
    public Product createProduct(long userId, ProductParams productParams, MultipartFile productImage) throws IOException {
        User user= userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        Category category = categoryJpaRepo.findById(productParams.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        String imageUrl = gcpService.uploadProductImage(productImage);

        Product product = Product.builder()
                .user(user)
                .userNickname(user.getUserNickname())
                .productName(productParams.getProductName())
                .productExplanation(productParams.getProductExplanation())
                .category(category)
                .productCost(productParams.getProductCost())
                .exchangeCostRange(productParams.getExchangeCostRange())
                .productImageUrl(imageUrl)
                .build();

        return productJpaRepo.save(product);
    }


    //상품 수정
    public Product modifyProduct(long productId, ProductParams productParams,MultipartFile productImage) throws IOException {
        Product product=productJpaRepo.findById(productId).orElseThrow(ProductNotFoundException::new);
        Category category = categoryJpaRepo.findById(productParams.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        // 이미지 변경된 경우
        if(!productParams.getProductImageUrl().equals(product.getProductImageUrl())){
            String updatedImageUrl = gcpService.updateProductImage(product.getProductImageUrl(),productImage);
            product.setProductImageUrl(updatedImageUrl);
        }

        product.setProductName(productParams.getProductName());
        product.setProductExplanation(productParams.getProductExplanation());
        product.setCategory(category);
        product.setProductCost(productParams.getProductCost());
        product.setExchangeCostRange(productParams.getExchangeCostRange());

        return product;
    }


    //사용자별 상품 리스트 조회
    public Page<Product> getProductsByUser(long userId, List<Character> productExchangeStatusCds, Pageable pageable){
        User user= userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);

        return productJpaRepo.findProductsByUser(user, productExchangeStatusCds, pageable);
    }


    // 내물품 리스트 조회(교환 신청하기 화면에서 상대방에게 이미 신청했는지 체크위해 사용)
    public Page<ProductDto> getProductsByUserWithCheckingIfRequestedAlready(long userId, long theirProductId, Pageable pageable) {
        User user= userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);

        List<Product> myProductsInWait = new ArrayList<>(productJpaRepo.findProductsByUserAndProductExchangeStatusCd(user, '0'));
        List<Long> productIdsAlreadyRequested = productJpaRepo.findProductsByUserAlreadyRequestedToTheirProduct(userId, theirProductId);

        List<ProductDto> result = convertToDtoList(myProductsInWait, productIdsAlreadyRequested);
        return convertToPage(result, pageable);
    }

    private List<ProductDto> convertToDtoList(List<Product> productList, List<Long> productIdsAlreadyRequested){
        List<ProductDto> productDtoList = productList.stream().map(
                        product -> new ProductDto(product, hasAlreadyRequested(productIdsAlreadyRequested, product.getProductId()))).collect(Collectors.toList());
        return productDtoList;
    }

    private boolean hasAlreadyRequested(List<Long> productIdsAlreadyRequested, Long findProductId) {
        return productIdsAlreadyRequested.contains(findProductId);
    }

    private Page<ProductDto> convertToPage(List<ProductDto> productDtoList, Pageable pageable) {
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), productDtoList.size());
        final Page<ProductDto> productDtoPage = new PageImpl<>(productDtoList.subList(start, end), pageable, productDtoList.size());

        return productDtoPage;
    }

    //상품 단건 조회
    public ProductDetailDto getOneProduct(long userId, long productId){
        User user = userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        Product product = productJpaRepo.findById(productId).orElseThrow(ProductNotFoundException::new);

        boolean like = heartJpaRepo.existsByUserAndProduct(user, product);
        return new ProductDetailDto(product,like);
    }


    //상품 검색
    public Page<ProductSearchDto> searchProducts(long userId, ProductSearchOptions productSearchOptions, Pageable pageable) {
        User user=userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Long> likeProductIds=productJpaRepo.findHeartProductIdsByUser(user);  //좋아요 상품 id 목록

        if (productSearchOptions.getProductName()!=null)
            productSearchOptions.setProductName('%'+productSearchOptions.getProductName()+'%');

        Page<Product> products = productSearchOptions.getIsMyProductIncluded()
                ? productJpaRepo.findProductsByOptions(productSearchOptions,pageable)
                : productJpaRepo.findProductsByOptionsExceptMyProduct(productSearchOptions,userId,pageable);

        Page<ProductSearchDto> result = products.map(product -> new ProductSearchDto(product,likeProductIds.contains(product.getProductId())));
        return result;
    }


    //상품 삭제
    public void deleteProduct(long productId){
        Product product=productJpaRepo.findById(productId).orElseThrow(ProductNotFoundException::new);
        // 교환상품 == null
        List<Exchange> requestedExchanges=exchangeJpaRepo.findExchangesByRequesterProduct(product);
        List<Exchange> acceptedExchanges=exchangeJpaRepo.findExchangesByAcceptorProduct(product);

        for (Exchange requestedExchange : requestedExchanges)
            requestedExchange.setRequesterProduct(null);

        for (Exchange acceptedExchange : acceptedExchanges)
            acceptedExchange.setAcceptorProduct(null);

        // 상품 삭제 notify ( 해당 상품에 교환신청한 유저들에게 )
        List<Request> requests=requestJpaRepo.findByAcceptorProductAndRequestStatusCd(product, '0');
        String deletionContent = notificationService.getProductDeletionNotiContent(product.getProductName());

        for (Request request : requests) {
            User receiver=request.getRequester();
            notificationService.createNotification(receiver, NotificationType.PRODUCT_DELETION_NOTI, deletionContent);
        }

        productJpaRepo.delete(product);
    }

    //특정 상품에 교환신청가능한 내 상품 리스트
    public Page<Product> getProductsForRequest(long userId, long acceptorProductId, Pageable pageable) {
        User user=userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        Product acceptorProduct=productJpaRepo.findById(acceptorProductId).orElseThrow(ProductNotFoundException::new);

        return productJpaRepo.findProductsForRequest(user, acceptorProduct, pageable);
    }
}
