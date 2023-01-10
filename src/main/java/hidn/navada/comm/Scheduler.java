package hidn.navada.comm;

import hidn.navada.exchange.ExchangeService;
import hidn.navada.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class Scheduler {
    private final UserService userService;
    private final ExchangeService exchangeService;

    // 매달 1일 00시 회원 레벨 결정
    @Scheduled(cron = "0 0 0 1 * *")
    public void updateUserLevelScheduler(){
        log.info("회원 레벨 결정 스케쥴러 작동 시작");
        userService.updateUserLevel();
        log.info("회원 레벨 결정 스케쥴러 작동 완료");
    }

    // 매일 00시 교환 완료 요청
    @Scheduled(cron = "0 0 0 * * *")
    public void requestExchangeCompletion(){
        log.info("교환 완료 요청 스케쥴러 작동 시작");
        exchangeService.requestExchangeCompletion();
        log.info("교환 완료 요청 스케쥴러 작동 완료");
    }
}


