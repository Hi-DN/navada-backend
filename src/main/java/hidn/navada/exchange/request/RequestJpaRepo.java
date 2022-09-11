package hidn.navada.exchange.request;

import hidn.navada.product.Product;
import hidn.navada.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestJpaRepo extends JpaRepository<Request,Long> {
    List<Request> findByAcceptorProductAndExchangeStatusCd(Product product, int exchangeStatusCd);
    List<Request> findByRequesterProductAndExchangeStatusCd(Product product, int exchangeStatusCd);

    @Query(value = "select r from Request r where r.requester=:requester and r.exchangeStatusCd in (0,2) and r.requesterDeniedRequestDeleteYn=false")
    Page<Request> findRequestsByRequester(@Param("requester") User requester, Pageable pageable);

    @Query(value = "select r from Request r where r.acceptor=:acceptor and r.exchangeStatusCd in (:exchangeStatusCds) and r.acceptorDeniedRequestDeleteYn=false")
    Page<Request> findRequestsByAcceptor(@Param("acceptor") User acceptor, @Param("exchangeStatusCds") List<Integer> exchangeStatusCds,Pageable pageable);

    @Query(value = "select r from Request r where r.requesterProduct=:requesterProduct and r.acceptor=:acceptor and r.exchangeStatusCd=0")
    List<Request> findRequestsByCertainProduct(@Param("requesterProduct") Product requesterProduct, @Param("acceptor") User acceptor);
}
