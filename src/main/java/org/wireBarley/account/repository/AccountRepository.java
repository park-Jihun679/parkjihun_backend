package org.wireBarley.account.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.wireBarley.account.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByAccountNo(String accountNo);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM AccountEntity a WHERE a.accountNo = :accountNo AND a.isDeleted = false")
    Optional<AccountEntity> findByAccountNoForUpdate(String accountNo);

    @Modifying
    @Query("""
        UPDATE AccountEntity a
        SET a.dailyTotalWithdrawal = 0, a.dailyTotalTransfer = 0, a.updatedDate = CURRENT_TIMESTAMP
        WHERE a.isDeleted = false
        """)
    int resetDailyLimits();
}
