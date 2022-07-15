package hidn.navada.exchange;

import hidn.navada.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExchangeJpaRepo extends JpaRepository<Exchange, Long> {
    /* 사용 안함 -> 추후 삭제하기
    @Query(value = "select * from exchange where exchange_complete_yn = 1 and acceptor_id = :acceptorId", nativeQuery = true)
    List<Exchange> findCompleteExchangesByAcceptorId(@Param("acceptorId") Long acceptorId);

    @Query(value = "select * from exchange where exchange_complete_yn = 0 and acceptor_id = :acceptorId", nativeQuery = true)
    List<Exchange> findUncompleteExchangesByAcceptorId(@Param("acceptorId") Long acceptorId);

    @Query(value = "select * from exchange where exchange_complete_yn = 1 and requester_id = :requesterId", nativeQuery = true)
    List<Exchange> findCompleteExchangesByRequesterId(@Param("requesterId") Long requesterId);

    @Query(value = "select * from exchange where exchange_complete_yn = 0 and requester_id = :requesterId", nativeQuery = true)
    List<Exchange> findUncompleteExchangesByRequesterId(@Param("requesterId") Long requesterId);
    */

    @Query(value = "select e from Exchange e where e.acceptor=:acceptor and e.acceptorHistoryDeleteYn=false")
    List<Exchange> findExchangesByAcceptor(@Param("acceptor") User acceptor);

    @Query(value = "select e from Exchange e where e.requester=:requester and e.requesterHistoryDeleteYn=false")
    List<Exchange> findExchangesByRequester(@Param("requester") User requester);
}
