package hidn.navada.exchange.request;

import hidn.navada.product.Product;
import hidn.navada.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RequestJpaRepo extends JpaRepository<Request,Long> {

    @Query(value = "select r from Request r join fetch r.acceptorProduct p join fetch r.requesterProduct p2 where r.requestId=:requestId")
    Optional<Request> findByIdWithProduct(@Param("requestId") long requestId);

    List<Request> findByAcceptorProductAndRequestStatusCd(Product product, char requestStatusCd);
    List<Request> findByRequesterProductAndRequestStatusCd(Product product, char requestStatusCd);

    @Query(value = "select r from Request r where r.requester=:requester and r.requestStatusCd in ('0','2') and r.requesterDeniedRequestDeleteYn=false")
    Page<Request> findRequestsByRequester(@Param("requester") User requester, Pageable pageable);

    @Query(value = "select r from Request r where r.acceptor=:acceptor and r.requestStatusCd in (:requestStatusCds) and r.acceptorDeniedRequestDeleteYn=false")
    Page<Request> findRequestsByAcceptor(@Param("acceptor") User acceptor, @Param("requestStatusCds") List<Character> requestStatusCds, Pageable pageable);

    @Query(value = "select r from Request r where r.requesterProduct=:requesterProduct and r.acceptor=:acceptor and r.requestStatusCd='0'")
    List<Request> findRequestsByCertainProduct(@Param("requesterProduct") Product requesterProduct, @Param("acceptor") User acceptor);
}
