package hidn.navada.exchange.request;

import hidn.navada.product.Product;
import hidn.navada.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestJpaRepo extends JpaRepository<Request,Long> {
    List<Request> findByAcceptorProductAndExchangeStatusCd(Product product, int exchangeStatusCd);

    @Query(value = "select r from Request r where r.requester=:requester and r.exchangeStatusCd in (0,2)")
    List<Request> findRequestsByRequester(@Param("requester") User requester);

    @Query(value = "select r from Request r where r.acceptor=:acceptor and r.exchangeStatusCd in (:exchangeStatusCds)")
    List<Request> findRequestsByAcceptor(@Param("acceptor") User acceptor, @Param("exchangeStatusCds") List<Integer> exchangeStatusCds);
}
