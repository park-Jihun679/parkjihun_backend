package org.wireBarley.account.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.wireBarley.account.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByAccountNo(String accountNo);

    @Query("UPDATE AccountEntity a SET a.dailyTotalWithdrawal = 0, a.dailyTotalTransfer = 0")
    int resetDailyLimits();
}
