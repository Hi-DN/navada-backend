package hidn.navada.exchange;

import hidn.navada.comm.exception.ExchangeNotFoundException;
import hidn.navada.comm.exception.ProductNotFoundException;
import hidn.navada.exchange.request.ExchangeRequest;
import hidn.navada.product.Product;
import hidn.navada.product.ProductJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final ExchangeJpaRepo exchangeJpaRepo;
    private final ProductJpaRepo productJpaRepo;

    public Exchange createExchange(ExchangeRequest exchangeRequest) {
        Exchange exchange = new Exchange();

        exchange.setAcceptor(exchangeRequest.getAcceptor());
        exchange.setAcceptorProduct(exchangeRequest.getAcceptorProduct());
        exchange.setRequester(exchangeRequest.getRequester());
        exchange.setRequesterProduct(exchangeRequest.getRequesterProduct());

        exchangeJpaRepo.save(exchange);

        return exchange;
    }

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
        }

        exchangeJpaRepo.save(exchange);

        return exchange;
    }

    public List<Exchange> getExchangeList(Long userId, Boolean isAcceptor, Boolean isComplete) {
        List<Exchange> exchangeList;

        if(isAcceptor)
            if(isComplete)
                exchangeList = exchangeJpaRepo.findCompleteExchangesByAcceptorId(userId);
            else
                exchangeList = exchangeJpaRepo.findUncompleteExchangesByAcceptorId(userId);
        else
            if(isComplete)
                exchangeList = exchangeJpaRepo.findCompleteExchangesByRequesterId(userId);
            else
                exchangeList = exchangeJpaRepo.findUncompleteExchangesByRequesterId(userId);

        return exchangeList;
    }

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
}
