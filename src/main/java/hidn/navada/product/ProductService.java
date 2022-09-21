package hidn.navada.product;

import hidn.navada.comm.exception.CategoryNotFoundException;
import hidn.navada.comm.exception.ProductNotFoundException;
import hidn.navada.comm.exception.UserNotFoundException;

import hidn.navada.product.category.Category;
import hidn.navada.product.category.CategoryJpaRepo;
import hidn.navada.user.User;
import hidn.navada.user.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //상품 등록
    public Product createProduct(long userId, ProductParams productParams){
        User user= userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        Category category = categoryJpaRepo.findById(productParams.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        Product product = Product.builder()
                .user(user)
                .userNickname(user.getUserNickname())
                .productName(productParams.getProductName())
                .productExplanation(productParams.getProductExplanation())
                .category(category)
                .productCost(productParams.getProductCost())
                .exchangeCostRange(productParams.getExchangeCostRange())
                .build();

        return productJpaRepo.save(product);
    }


    //상품 수정
    public Product modifyProduct(long productId, ProductParams productParams){
        Product product=productJpaRepo.findById(productId).orElseThrow(ProductNotFoundException::new);
        Category category = categoryJpaRepo.findById(productParams.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        product.setProductName(productParams.getProductName());
        product.setProductExplanation(productParams.getProductExplanation());
        product.setCategory(category);
        product.setProductCost(productParams.getProductCost());
        product.setExchangeCostRange(productParams.getExchangeCostRange());

        return product;
    }


    //사용자별 상품 리스트 조회
    public Page<Product> getProductsByUser(long userId, Pageable pageable){
        User user= userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);

        return productJpaRepo.findProductsByUser(user,pageable);
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
    public Product getOneProduct(long productId){
        return productJpaRepo.findById(productId).orElseThrow(ProductNotFoundException::new);
    }


    //상품 검색
    public Page<ProductSearchDto> searchProducts(long userId,String productName, List<Long> categoryIds, Integer lowerCostBound, Integer upperCostBound, Pageable pageable) {
        User user=userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Long> likeProductIds=productJpaRepo.findHeartProductIdsByUser(user);  //좋아요 상품 id 목록
        Page<Product> products;

        // 전체 대상 검색
        if(categoryIds.isEmpty() && lowerCostBound==null)
            products= productJpaRepo.findProductsByProductNameContains(productName,pageable);
        // 카테고리별 검색
        else if(!categoryIds.isEmpty())
            products= productJpaRepo.searchProductsByNameAndCategory(productName,categoryIds,pageable);
        // 가격범위별 검색
        else if(!(lowerCostBound==null))
            products= productJpaRepo.searchProductsByNameAndCost(productName,lowerCostBound,upperCostBound,pageable);
        // 카테고리 + 가격범위
        else products= productJpaRepo.searchProductsByNameAndCategoryAndCost(productName,categoryIds,lowerCostBound,upperCostBound,pageable);

        Page<ProductSearchDto> result = products.map(product -> new ProductSearchDto(product,likeProductIds.contains(product.getProductId())));
        return result;
    }


    //상품 삭제
    public void deleteProduct(long productId){
        Product product=productJpaRepo.findById(productId).orElseThrow(ProductNotFoundException::new);
        productJpaRepo.delete(product);
    }

    //특정 상품에 교환신청가능한 내 상품 리스트
    public Page<Product> getProductsForRequest(long userId, long acceptorProductId, Pageable pageable) {
        User user=userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        Product acceptorProduct=productJpaRepo.findById(acceptorProductId).orElseThrow(ProductNotFoundException::new);

        return productJpaRepo.findProductsForRequest(user, acceptorProduct, pageable);
    }
}
