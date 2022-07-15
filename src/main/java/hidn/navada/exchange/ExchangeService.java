package hidn.navada.exchange;

import hidn.navada.comm.exception.ExchangeNotFoundException;
import hidn.navada.comm.exception.ProductNotFoundException;
import hidn.navada.comm.exception.UserNotFoundException;
import hidn.navada.exchange.request.Request;
import hidn.navada.product.Product;
import hidn.navada.product.ProductJpaRepo;
import hidn.navada.user.User;
import hidn.navada.user.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ExchangeService {
    private final ExchangeJpaRepo exchangeJpaRepo;
    private final ProductJpaRepo productJpaRepo;
    private final UserJpaRepo userJpaRepo;

    //교환 성립
    public Exchange createExchange(Request request) {
        Exchange exchange = new Exchange();

        exchange.setAcceptor(request.getAcceptor());
        exchange.setAcceptorProduct(request.getAcceptorProduct());
        exchange.setRequester(request.getRequester());
        exchange.setRequesterProduct(request.getRequesterProduct());

        exchangeJpaRepo.save(exchange);

        return exchange;
    }

    //교환 완료
    public Exchange completeExchange(Long exchangeId, Boolean isAcceptor) {
        Exchange exchange = exchangeJpaRepo.findById(exchangeId).orElseThrow(ExchangeNotFoundException::new);
        if(isAcceptor) {
            // 수락자가 교환 완료를 누른 경우
            exchange.setAcceptorConfirmYn(true);

        } else {
            // 요청자가 교환 완료를 누른 경우
            exchange.setRequesterConfirmYn(true);
        }

        // 둘다 교환 완료인 경우
        if(exchange.isAcceptorConfirmYn() && exchange.isRequesterConfirmYn()) {
            exchange.setExchangeCompleteYn(true);
            exchange.setExchangeCompleteDt(LocalDateTime.now());

            Product acceptorProduct = productJpaRepo.findById(exchange.getAcceptorProduct().getProductId()).orElseThrow(ProductNotFoundException::new);
            Product requesterProduct = productJpaRepo.findById(exchange.getRequesterProduct().getProductId()).orElseThrow(ProductNotFoundException::new);

            // 상품 상태 교환 완료로 변경
            acceptorProduct.setProductStatusCd(2);
            requesterProduct.setProductStatusCd(2);

            productJpaRepo.save(acceptorProduct);
            productJpaRepo.save(requesterProduct);

            updateUserInfo(exchange);
        }

        exchangeJpaRepo.save(exchange);

        return exchange;
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
    public List<Exchange> getExchangeList(Long userId) {
        User user=userJpaRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Exchange> acceptedExchangeList;
        List<Exchange> requestedExchangeList;

        acceptedExchangeList = exchangeJpaRepo.findExchangesByAcceptor(user);
        requestedExchangeList = exchangeJpaRepo.findExchangesByRequester(user);

        List<Exchange> result = new ArrayList<>();
        result.addAll(acceptedExchangeList);
        result.addAll(requestedExchangeList);

        return result;
    }

    //교환 평점 부여
    public Exchange rateExchange(Long exchangeId, Boolean isAcceptor, float rating) {
        Exchange exchange = exchangeJpaRepo.findById(exchangeId).orElseThrow(ExchangeNotFoundException::new);
        if(isAcceptor) {
            // 수락자가 요청자에게 평점을 주고있는 경우
            exchange.setRequesterRating(rating);

        } else {
            // 요청자가 수락자에게 평점을 주고있는 경우
            exchange.setAcceptorRating(rating);
        }

        exchangeJpaRepo.save(exchange);

        return exchange;
    }

    //교환 내역 삭제
    public Exchange deleteExchangeHistory(Long exchangeId, Boolean isAcceptor) {
        Exchange exchange = exchangeJpaRepo.findById(exchangeId).orElseThrow(ExchangeNotFoundException::new);
        if(isAcceptor) {
            // 수락자가 교환 내역을 삭제하려는 경우
            exchange.setAcceptorHistoryDeleteYn(true);

        } else {
            // 요청자가 교환 내역을 삭제하려는 경우
            exchange.setRequesterHistoryDeleteYn(true);
        }

        exchangeJpaRepo.save(exchange);

        return exchange;
    }
}
