package hidn.navada.user;

import hidn.navada.comm.exception.ProductNotFoundException;
import hidn.navada.comm.exception.UserNotFoundException;
import hidn.navada.product.Product;
import hidn.navada.product.ProductJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepo userJpaRepo;
    private final ProductJpaRepo productJpaRepo;

    // 회원 단건 조회
    public User getOneUser(long userId){
        return userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    // 회원 단건 조회 by productId
    public User getOneUserByProductId(long productId) {
        Product product=productJpaRepo.findById(productId).orElseThrow(ProductNotFoundException::new);
        return product.getUser();
    }
}
