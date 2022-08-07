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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    //상품 단건 조회
    public Product getOneProduct(long productId){
        return productJpaRepo.findById(productId).orElseThrow(ProductNotFoundException::new);
    }


    //상품 검색
    public Page<Product> searchProducts(String productName, List<Long> categoryIds, Integer lowerCostBound, Integer upperCostBound, Pageable pageable) {
        // 전체 대상 검색
        if(categoryIds.isEmpty() && lowerCostBound==null)
            return productJpaRepo.findProductsByProductNameContains(productName,pageable);
        // 카테고리별 검색
        else if(!categoryIds.isEmpty())
            return productJpaRepo.searchProductsByNameAndCategory(productName,categoryIds,pageable);
        // 가격범위별 검색
        else if(!(lowerCostBound==null))
            return productJpaRepo.searchProductsByNameAndCost(productName,lowerCostBound,upperCostBound,pageable);
        // 카테고리 + 가격범위
        else return productJpaRepo.searchProductsByNameAndCategoryAndCost(productName,categoryIds,lowerCostBound,upperCostBound,pageable);
    }


    //상품 삭제
    public void deleteProduct(long productId){
        Product product=productJpaRepo.findById(productId).orElseThrow(ProductNotFoundException::new);
        productJpaRepo.delete(product);
    }

}
