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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //좋아요 취소 by heartId
    public void cancelHeartByHeartId(long heartId){
        Heart heart= heartJpaRepo.findById(heartId).orElseThrow(HeartNotFoundException::new);
        Product product=heart.getProduct();

        product.setHeartNum(Math.max(product.getHeartNum()-1,0));     //좋아요 수 감소
        heartJpaRepo.delete(heart);
    }

    //좋아요 취소 by product and user
    public void cancelHeartByProductAndUser(long productId, long userId) {
        User user=userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        Product product=productJpaRepo.findById(productId).orElseThrow(ProductNotFoundException::new);

        Heart heart=heartJpaRepo.findByProductAndUser(product,user);
        product.setHeartNum(Math.max(product.getHeartNum()-1,0));

        heartJpaRepo.delete(heart);
    }

    //사용자별 좋아요 내역 조회
    public Page<Heart> getHeartsByUser(long userId, boolean showAll, Pageable pageable){
        User user=userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);

        if(showAll) return heartJpaRepo.findHeartsByUser(user,pageable);
        else return heartJpaRepo.findHeartsByUserAndProductExchangeStatusCd(user,pageable);
    }
}
