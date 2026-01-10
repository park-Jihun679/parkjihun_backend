package org.wireBarley.account.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.wireBarley.account.repository.AccountRepository;

@Component
@RequiredArgsConstructor
@Transactional
public class AccountScheduler {

    private final AccountRepository accountRepository;

    // 매일 자정 일일 한도 초기화
    @Scheduled(cron = "0 0 0 * * *")
    public int resetDailyLimits() {
        return accountRepository.resetDailyLimits();
    }
}