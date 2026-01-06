package org.wireBarley.transaction.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.wireBarley.account.dto.AccountCreateDTO;
import org.wireBarley.account.dto.AccountDTO;
import org.wireBarley.account.service.AccountService;
import org.wireBarley.transaction.domain.TransactionType;
import org.wireBarley.transaction.dto.TransactionCreateDTO;
import org.wireBarley.transaction.dto.TransactionDTO;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    AccountDTO account1;
    AccountDTO account2;

    @BeforeEach
    public void setup() {

        AccountCreateDTO createDTO = AccountCreateDTO.builder().build();

        account1 = accountService.createAccount(createDTO);
        account2 = accountService.createAccount(createDTO);
    }

    @Test
    @DisplayName("입금")
    void depositTransaction() {
        // given
        BigDecimal depositAmount = new BigDecimal("10000"); // 1만원

        TransactionCreateDTO createDTO = TransactionCreateDTO.builder()
            .type(TransactionType.DEPOSIT)
            .accountNo(account1.getAccountNo())
            .description("입금")
            .amount(depositAmount)
            .build();

        // when
        TransactionDTO result = transactionService.createTransaction(createDTO);
        AccountDTO resultAccount = accountService.getAccount(account1.getAccountId());

        // then
        assertNotNull(result);

        assertEquals(resultAccount.getAccountId(), result.getAccountId());
        assertEquals(resultAccount.getAccountNo(), result.getAccountNo());
        assertEquals("입금", result.getDescription());
        assertEquals(depositAmount, result.getAmount());
        assertEquals(BigDecimal.ZERO, result.getBeforeBalance());
        assertEquals(depositAmount, result.getAfterBalance());

        assertEquals(resultAccount.getBalance(), depositAmount);

    }
}