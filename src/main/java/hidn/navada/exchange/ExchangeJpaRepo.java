package hidn.navada.exchange;

import hidn.navada.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExchangeJpaRepo extends JpaRepository<Exchange, Long> {
    @Query(value = "select e from Exchange e where (e.acceptor=:user and e.acceptorHistoryDeleteYn=false) or (e.requester=:user and e.requesterHistoryDeleteYn=false)")
    Page<Exchange> findExchangePagesByUser(@Param("user") User user, Pageable pageable);

    @Query(value = "select e from Exchange e where (e.requester=:user and e.requesterHistoryDeleteYn=false)")
    Page<Exchange> findExchangePagesByRequester(@Param("user") User user, Pageable pageable);

    @Query(value = "select e from Exchange e where (e.acceptor=:user and e.acceptorHistoryDeleteYn=false)")
    Page<Exchange> findExchangePagesByAcceptor(@Param("user") User user, Pageable pageable);
}
