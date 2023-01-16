package hidn.navada.exchange;

import hidn.navada.comm.enums.NotificationType;
import hidn.navada.comm.exception.CanNotCancelExchangeException;
import hidn.navada.comm.exception.ExchangeNotFoundException;
import hidn.navada.comm.exception.ProductNotFoundException;
import hidn.navada.comm.exception.UserNotFoundException;
import hidn.navada.exchange.request.Request;
import hidn.navada.notification.NotificationService;
import hidn.navada.product.Product;
import hidn.navada.product.ProductJpaRepo;
import hidn.navada.user.User;
import hidn.navada.user.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ExchangeService {
    private final ExchangeJpaRepo exchangeJpaRepo;
    private final ProductJpaRepo productJpaRepo;
    private final UserJpaRepo userJpaRepo;
    private final NotificationService notificationService;

    //교환 성립
    public Exchange createExchange(Request request) {
        Exchange exchange = Exchange.builder()
                .acceptor(request.getAcceptor())
                .acceptorProduct(request.getAcceptorProduct())
                .requester(request.getRequester())
                .requesterProduct(request.getRequesterProduct())
                .build();

        return exchangeJpaRepo.save(exchange);
    }

    //교환 완료
    public Exchange completeExchange(Long exchangeId, Boolean isAcceptor) {
        Exchange exchange = exchangeJpaRepo.findByIdWithProduct(exchangeId).orElseThrow(ExchangeNotFoundException::new);

        if(isAcceptor) exchange.setAcceptorConfirmYn(true);
        else exchange.setRequesterConfirmYn(true);

        // 둘다 교환 완료인 경우
        if(exchange.isAcceptorConfirmYn() && exchange.isRequesterConfirmYn()) {
            exchange.setExchangeStatusCd('2'); //2. 교환 완료
            exchange.setExchangeCompleteDt(LocalDateTime.now());

            Product acceptorProduct = productJpaRepo.findById(exchange.getAcceptorProduct().getProductId()).orElseThrow(ProductNotFoundException::new);
            Product requesterProduct = productJpaRepo.findById(exchange.getRequesterProduct().getProductId()).orElseThrow(ProductNotFoundException::new);

            // 상품 상태 교환 완료로 변경
            acceptorProduct.setProductExchangeStatusCd('2');
            requesterProduct.setProductExchangeStatusCd('2');

            updateUserInfo(exchange);
        }
        else{
            sendCompleteNoti(exchange, isAcceptor);
        }

        return exchange;
    }

    // 교환 완료 알림
    private void sendCompleteNoti(Exchange exchange, boolean isAcceptor){
        User completedUser = isAcceptor ? exchange.getAcceptor():exchange.getRequester();
        User notCompletedUser = isAcceptor ? exchange.getRequester():exchange.getAcceptor();

        String completedContent= notificationService.getCompleteNotiContent(completedUser.getUserName(), exchange);
        notificationService.createNotification(notCompletedUser, NotificationType.COMPLETE_NOTI,completedContent);
    }

    //교환 완료 시 거래횟수, 평균평점 업데이트
    private void updateUserInfo(Exchange exchange) {
        User acceptor = userJpaRepo.findById(exchange.getAcceptor().getUserId()).orElseThrow(UserNotFoundException::new);
        User requester = userJpaRepo.findById(exchange.getRequester().getUserId()).orElseThrow(UserNotFoundException::new);

        float acceptorRating=exchange.getAcceptorRating();
        float requesterRating=exchange.getRequesterRating();

        float prevAcceptorRating = acceptor.getUserRating()*acceptor.getUserRatingCount();
        float prevRequesterRating = requester.getUserRating()*requester.getUserRatingCount();

        acceptor.setUserTradeCount(acceptor.getUserTradeCount()+1);
        requester.setUserTradeCount(requester.getUserTradeCount()+1);

        //평점을 부여한 경우
        if(acceptorRating>-1){
            acceptor.setUserRatingCount(acceptor.getUserRatingCount()+1);
            acceptor.setUserRating((prevAcceptorRating+acceptorRating)/acceptor.getUserRatingCount());
        }
        if(requesterRating>-1){
            requester.setUserRatingCount(requester.getUserRatingCount()+1);
            requester.setUserRating((prevRequesterRating+requesterRating)/requester.getUserTradeCount());
        }
    }

    //교환목록조회(교환중, 교환완료)
    public Page<ExchangeDto> getExchangeList(Long userId, List<Character> exchangeStatusCds, Boolean viewOnlySentElseGot, Pageable pageable) {
        User user = userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        Page<Exchange> exchanges;

        if(viewOnlySentElseGot == null)
            exchanges = exchangeJpaRepo.findExchangePagesByUser(user, exchangeStatusCds, pageable);
        else {
            exchanges = (viewOnlySentElseGot)
                    ? exchangeJpaRepo.findExchangePagesByRequester(user, exchangeStatusCds, pageable)
                    : exchangeJpaRepo.findExchangePagesByAcceptor(user, exchangeStatusCds, pageable);
        }
        return convertToDto(exchanges);
    }

    //교환 평점 부여
    public Exchange rateExchange(Long exchangeId, Boolean isAcceptor, float rating) {
        Exchange exchange = exchangeJpaRepo.findByIdWithProduct(exchangeId).orElseThrow(ExchangeNotFoundException::new);

        if(isAcceptor) exchange.setRequesterRating(rating);
        else exchange.setAcceptorRating(rating);

        return exchange;
    }

    //교환 내역 삭제
    public Exchange deleteExchangeHistory(Long exchangeId, Boolean isAcceptor) {
        Exchange exchange = exchangeJpaRepo.findByIdWithProduct(exchangeId).orElseThrow(ExchangeNotFoundException::new);

        if(isAcceptor) exchange.setAcceptorHistoryDeleteYn(true);
        else exchange.setRequesterHistoryDeleteYn(true);

        return exchange;
    }

    public Page<ExchangeDto> convertToDto(Page<Exchange> exchangeList){
        return exchangeList.map(ExchangeDto::new);
    }

    //교환 취소
    public Exchange cancelExchange(Long exchangeId) {
        Exchange exchange = exchangeJpaRepo.findByIdWithProduct(exchangeId).orElseThrow(ExchangeNotFoundException::new);

        if(exchange.isAcceptorConfirmYn() || exchange.isRequesterConfirmYn())
            throw new CanNotCancelExchangeException();

        else {
            exchange.setExchangeStatusCd('3');  //3. 교환 취소
            exchange.getAcceptorProduct().setProductExchangeStatusCd('0');
            exchange.getRequesterProduct().setProductExchangeStatusCd('0');
        }

        return exchange;
    }

    // 교환 완료 요청 by 스케쥴러
    public void requestExchangeCompletion() {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        List<Exchange> exchanges = exchangeJpaRepo.findExchangesForPeriodicCompleteNoti();

        for (Exchange exchange : exchanges) {
            LocalDateTime createdDay = exchange.getCreatedDate().toLocalDate().atStartOfDay();
            int passedDays = (int) Duration.between(createdDay,today).toDays();

            // 7일 경과시 알림
            if(is7DaysPassed(passedDays)){
                    sendPeriodicCompleteNoti(exchange);
            }
        }
    }

    private boolean is7DaysPassed(int days){
        return days!=0 && days%7==0;
    }

    private void sendPeriodicCompleteNoti(Exchange exchange){
        User acceptor = exchange.getAcceptor();
        User requester = exchange.getRequester();
        String content = notificationService.getPeriodicCompleteNotiContent(exchange);

        notificationService.createNotification(acceptor,NotificationType.PERIODIC_COMPLETE_NOTI,content);
        notificationService.createNotification(requester,NotificationType.PERIODIC_COMPLETE_NOTI,content);
    }

}
