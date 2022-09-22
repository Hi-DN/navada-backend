package hidn.navada.exchange;

import hidn.navada.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExchangeJpaRepo extends JpaRepository<Exchange, Long> {

    @Query(value = "select e from Exchange e join fetch e.requesterProduct join fetch e.acceptorProduct " +
            "where (e.acceptor=:user and e.acceptorHistoryDeleteYn=false) or (e.requester=:user and e.requesterHistoryDeleteYn=false)",
    countQuery = "select count(e) from Exchange e inner join e.requesterProduct inner join e.acceptorProduct " +
            "where (e.acceptor=:user and e.acceptorHistoryDeleteYn=false) or (e.requester=:user and e.requesterHistoryDeleteYn=false)")
    Page<Exchange> findExchangePagesByUser(@Param("user") User user, Pageable pageable);

    @Query(value = "select e from Exchange e join fetch e.requesterProduct join fetch e.acceptorProduct " +
            "where (e.requester=:user and e.requesterHistoryDeleteYn=false)",
    countQuery = "select count(e) from Exchange e inner join e.requesterProduct inner join e.acceptorProduct " +
            "where (e.requester=:user and e.requesterHistoryDeleteYn=false)")
    Page<Exchange> findExchangePagesByRequester(@Param("user") User user, Pageable pageable);

    @Query(value = "select e from Exchange e join fetch e.acceptorProduct join fetch e.requesterProduct " +
            "where (e.acceptor=:user and e.acceptorHistoryDeleteYn=false)",
    countQuery ="select count(e) from Exchange e inner join e.acceptorProduct inner join e.requesterProduct " +
            "where (e.acceptor=:user and e.acceptorHistoryDeleteYn=false)" )
    Page<Exchange> findExchangePagesByAcceptor(@Param("user") User user, Pageable pageable);
}
