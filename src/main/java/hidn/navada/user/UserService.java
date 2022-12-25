package hidn.navada.user;

import hidn.navada.comm.exception.UserNotFoundException;
import hidn.navada.oauth.OAuth;
import hidn.navada.oauth.OAuthJpaRepo;
import hidn.navada.oauth.SigninPlatform;
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
    private final OAuthJpaRepo oauthJpaRepo;

    // 회원 가입
    public User createUser(UserParams params) {
        User newUser = userJpaRepo.save(User.create(params));
        OAuth newOauth = OAuth.create(newUser, params.getUserEmail(), SigninPlatform.valueOf(params.getSigninPlatform()));
        oauthJpaRepo.save(newOauth);
        return newUser;
    }

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
