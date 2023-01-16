package hidn.navada.exchange;

import hidn.navada.product.Product;
import hidn.navada.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExchangeJpaRepo extends JpaRepository<Exchange, Long> {
    List<Exchange> findExchangesByRequesterUserId(@Param("requesterId") Long requesterId);

    List<Exchange> findExchangesByAcceptorUserId(@Param("acceptorId") Long acceptorId);

    @Query(value = "select e from Exchange e join fetch e.acceptorProduct p join fetch e.requesterProduct p2 where e.exchangeId=:exchangeId")
    Optional<Exchange> findByIdWithProduct(@Param("exchangeId") long exchangeId);


    @Query(value = "select e from Exchange e where e.exchangeStatusCd='1' and e.acceptorConfirmYn=false and e.requesterConfirmYn=false")
    List<Exchange> findExchangesForPeriodicCompleteNoti();

    @Query(value = "select e from Exchange e join fetch e.requesterProduct join fetch e.acceptorProduct " +
            "where ((e.acceptor=:user and e.acceptorHistoryDeleteYn=false) or (e.requester=:user and e.requesterHistoryDeleteYn=false))" +
            " and (coalesce(:exchangeStatusCds,null) is null or (e.exchangeStatusCd in :exchangeStatusCds))",
    countQuery = "select count(e) from Exchange e inner join e.requesterProduct inner join e.acceptorProduct " +
            "where ((e.acceptor=:user and e.acceptorHistoryDeleteYn=false) or (e.requester=:user and e.requesterHistoryDeleteYn=false))" +
            " and (coalesce(:exchangeStatusCds,null) is null or (e.exchangeStatusCd in :exchangeStatusCds))")
    Page<Exchange> findExchangePagesByUser(@Param("user") User user, @Param("exchangeStatusCds") List<Character> exchangeStatusCds, Pageable pageable);

    @Query(value = "select e from Exchange e join fetch e.requesterProduct join fetch e.acceptorProduct " +
            "where (e.requester=:user and e.requesterHistoryDeleteYn=false)" +
            " and (coalesce(:exchangeStatusCds,null) is null or (e.exchangeStatusCd in :exchangeStatusCds))",
    countQuery = "select count(e) from Exchange e inner join e.requesterProduct inner join e.acceptorProduct " +
            "where (e.requester=:user and e.requesterHistoryDeleteYn=false)" +
            " and (coalesce(:exchangeStatusCds,null) is null or (e.exchangeStatusCd in :exchangeStatusCds))")
    Page<Exchange> findExchangePagesByRequester(@Param("user") User user, @Param("exchangeStatusCds") List<Character> exchangeStatusCds, Pageable pageable);

    @Query(value = "select e from Exchange e join fetch e.acceptorProduct join fetch e.requesterProduct " +
            "where (e.acceptor=:user and e.acceptorHistoryDeleteYn=false)" +
            " and (coalesce(:exchangeStatusCds,null) is null or (e.exchangeStatusCd in :exchangeStatusCds))",
    countQuery ="select count(e) from Exchange e inner join e.acceptorProduct inner join e.requesterProduct " +
            "where (e.acceptor=:user and e.acceptorHistoryDeleteYn=false)" +
            " and (coalesce(:exchangeStatusCds,null) is null or (e.exchangeStatusCd in :exchangeStatusCds))" )
    Page<Exchange> findExchangePagesByAcceptor(@Param("user") User user, @Param("exchangeStatusCds") List<Character> exchangeStatusCds, Pageable pageable);

    List<Exchange> findExchangesByAcceptorProduct(Product product);
    List<Exchange> findExchangesByRequesterProduct(Product product);
}
