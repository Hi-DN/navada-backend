package hidn.navada.heart;

import hidn.navada.comm.exception.HeartNotFoundException;
import hidn.navada.comm.exception.ProductNotFoundException;
import hidn.navada.comm.exception.UserNotFoundException;
import hidn.navada.product.Product;
import hidn.navada.product.ProductJpaRepo;
import hidn.navada.user.User;
import hidn.navada.user.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HeartService {
    private final HeartJpaRepo heartJpaRepo;
    private final UserJpaRepo userJpaRepo;
    private final ProductJpaRepo productJpaRepo;

    //좋아요 등록
    public Heart saveHeart(long productId, long userId){
        User user=userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        Product product=productJpaRepo.findById(productId).orElseThrow(ProductNotFoundException::new);

        Heart heart= Heart.builder()
                .user(user)
                .product(product)
                .build();

        product.setHeartNum(product.getHeartNum()+1);   //좋아요 수 증가
        return heartJpaRepo.save(heart);
    }

    //좋아요 취소
    public void cancelHeart(long heartId){
        Heart heart= heartJpaRepo.findById(heartId).orElseThrow(HeartNotFoundException::new);
        Product product=heart.getProduct();

        product.setProductCost(product.getProductCost()-1);     //좋아요 수 감소
        heartJpaRepo.delete(heart);
    }

    //사용자별 좋아요 내역 조회
    public List<Heart> getHeartsByUser(long userId){
        User user=userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        return heartJpaRepo.findHeartsByUser(user);
    }
}
