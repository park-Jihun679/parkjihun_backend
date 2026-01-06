package org.wireBarley.account.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.wireBarley.account.service.AccountService.BANK_CODE;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.wireBarley.account.dto.AccountCreateDTO;
import org.wireBarley.account.dto.AccountDTO;
import org.wireBarley.common.exception.DataNotFoundException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class AccountServiceTest {


    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AccountService accountService;

    @Test
    @DisplayName("계좌 생성")
    public void createAccount() {
        // given
        AccountCreateDTO createDTO = AccountCreateDTO.builder().build();

        // when
        AccountDTO created = accountService.createAccount(createDTO);

        entityManager.flush();
        entityManager.clear();

        // then
        AccountDTO result = accountService.getAccount(created.getAccountId());

        assertNotNull(result.getAccountId());
        assertNotNull(result.getAccountNo());
        assertNotNull(result.getCreatedDate());

        String resultAccountName =
            result.getAccountNo().substring(result.getAccountNo().length() - 6) + "_계좌";
        assertEquals(resultAccountName, result.getAccountName());
        assertEquals(BigDecimal.ZERO, result.getDailyTotalTransfer());
        assertEquals(BigDecimal.ZERO, result.getDailyTotalWithdrawal());
        assertEquals(BANK_CODE, result.getBankCode());

        assertFalse(result.isDeleted());
    }


    @Test
    @DisplayName("계좌 삭제")
    public void deleteAccount() {
        // given
        AccountDTO createdAccount = accountService.createAccount(
            AccountCreateDTO.builder().build());

        // when
        AccountDTO result = accountService.deleteAccount(createdAccount.getAccountId());

        // then
        assertTrue(result.isDeleted());
        assertThrows(DataNotFoundException.class,
            () -> accountService.getAccount(createdAccount.getAccountId()));
        assertThrows(DataNotFoundException.class,
            () -> accountService.deleteAccount(createdAccount.getAccountId()));
    }
}
