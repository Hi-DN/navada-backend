package hidn.navada.user;

import hidn.navada.comm.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepo userJpaRepo;

    // 회원 단건 조회
    public User getOneUser(long userId){
        return userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    // 회원 단건 조회 by productId
    public User getOneUserByProductId(long productId) {
        return userJpaRepo.findUserByProduct(productId);
    }

    // 회원 정보 수정
    public User modifyUser(Long userId, UserParams params) {
        User user = userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        user.update(params);
        return user;
    }
}
