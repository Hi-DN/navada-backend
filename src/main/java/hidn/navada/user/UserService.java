package hidn.navada.user;

import hidn.navada.comm.enums.UserLevel;
import hidn.navada.comm.exception.UserExistException;
import hidn.navada.comm.exception.UserNotFoundException;
import hidn.navada.exchange.Exchange;
import hidn.navada.exchange.ExchangeJpaRepo;
import hidn.navada.oauth.OAuth;
import hidn.navada.oauth.OAuthJpaRepo;
import hidn.navada.oauth.SignInPlatform;
import hidn.navada.product.Product;
import hidn.navada.product.ProductJpaRepo;
import hidn.navada.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepo userJpaRepo;
    private final ExchangeJpaRepo exchangeJpaRepo;
    private final ProductJpaRepo productJpaRepo;
    private final ProductService productService;
    private final OAuthJpaRepo oauthJpaRepo;

    // 회원 가입
    public User signUp(UserParams params) {
        String userPhoneNum = params.getUserPhoneNum().replaceAll("-", "");
        Optional<User> findUser = userJpaRepo.findUserByUserPhoneNum(userPhoneNum);

        if(findUser.isPresent()) {
            throw new UserExistException("이미 존재하는 회원입니다.");
        } else {
            return createUser(params);
        }
    }

    private User createUser(UserParams params) {
        User newUser = userJpaRepo.save(User.create(params));
        OAuth newOauth = OAuth.create(newUser, params.getUserEmail(), SignInPlatform.valueOf(params.getSignInPlatform()));
        oauthJpaRepo.save(newOauth);
        return newUser;
    }

    // 닉네임 중복 체크
    public boolean checkNicknameUsable(String nickname) {
        Optional<User> findUser = userJpaRepo.findUserByUserNickname(nickname);
        return findUser.isEmpty();
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
    public User modifyUser(Long userId, UserUpdateParams params) {
        User user = userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        user.update(params);
        return user;
    }

    // 알림 확인 여부 조회
    public Boolean getUserNotificationReadYn(long userId) {
        User user = userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        return user.isUserNotificationReadYn();
    }

    // 알림 확인 완료
    public void updateNotificationReadYn(long userId) {
        User user = userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setUserNotificationReadYn(true);
    }

    // 회원 탈퇴
    public void deleteUser(Long userId) {
        User user = userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        deleteProductsByUser(userId);
        setExchangesUserNull(userId);
        userJpaRepo.delete(user);
    }

    private void deleteProductsByUser(Long userId) {
        List<Product> products = productJpaRepo.findProductsByUserUserId(userId);
        products.forEach((product) -> productService.deleteProduct(product.getProductId()));
    }

    private void setExchangesUserNull(Long userId) {
        List<Exchange> exchangesByRequester = exchangeJpaRepo.findExchangesByRequesterUserId(userId);
        exchangesByRequester.forEach(Exchange::deleteRequester);

        List<Exchange> exchangesByAcceptor = exchangeJpaRepo.findExchangesByAcceptorUserId(userId);
        exchangesByAcceptor.forEach(Exchange::deleteAccepter);
    }

    // 회원 레벨 결정 by 스케쥴러
    public void updateUserLevel() {
        List<User> userList=userJpaRepo.findAll();
        userList.sort(userLevelComparator);

        int totalNumOfUsers = userList.size();
        int lastHeadManIndex=0; // headMan 1명
        int lastNativeIndex = (int) (totalNumOfUsers*0.2); // native 20%
        int lastResidentIndex = (int) (totalNumOfUsers*0.5); // resident 누적 50%

        IntStream.range(0, totalNumOfUsers).forEach(index -> {
            User user = userList.get(index);
            if (index <= lastHeadManIndex) user.setUserLevel(UserLevel.LV4_HEADMAN);
            else if (index <= lastNativeIndex) user.setUserLevel(UserLevel.LV3_NATIVE);
            else if (index <= lastResidentIndex) user.setUserLevel(UserLevel.LV2_RESIDENT);
            else user.setUserLevel(UserLevel.LV1_OUTSIDER);
        });
    }

    Comparator<User> userLevelComparator= (User u1, User u2) -> {
        double u1LevelScore = u1.getUserTradeCount()*70 + u1.getUserRating()*30;
        double u2LevelScore = u2.getUserTradeCount()*70 + u2.getUserRating()*30;

        return u1LevelScore<u2LevelScore ? 1 : -1;
    };

}
