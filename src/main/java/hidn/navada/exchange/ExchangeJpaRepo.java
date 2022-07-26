package hidn.navada.exchange;

import hidn.navada.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query(value = "select e from Exchange e where (e.acceptor=:user and e.acceptorHistoryDeleteYn=false) or (e.requester=:user and e.requesterHistoryDeleteYn=false)")
    Page<Exchange> findExchangePagesByUser(@Param("user") User user, Pageable pageable);

}
