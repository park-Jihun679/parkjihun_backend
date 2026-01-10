package org.wireBarley.transaction.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import org.wireBarley.common.exception.ErrorCode;
import org.wireBarley.common.exception.InvalidRequestException;
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

    BigDecimal TRANSFER_FEE = new BigDecimal("1.01");

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

        assertEquals(depositAmount, resultAccount.getBalance());
    }

    @Test
    @DisplayName("출금")
    void withdrawalTransaction() {
        // given
        BigDecimal depositAmount = new BigDecimal("10000"); // 1만원

        TransactionCreateDTO deposit = TransactionCreateDTO.builder()
            .type(TransactionType.DEPOSIT)
            .accountNo(account1.getAccountNo())
            .amount(depositAmount)
            .build();
        transactionService.createTransaction(deposit);

        BigDecimal withdrawalAmount = new BigDecimal("5000"); // 5천원

        TransactionCreateDTO withdrawal = TransactionCreateDTO.builder()
            .type(TransactionType.WITHDRAWAL)
            .accountNo(account1.getAccountNo())
            .amount(withdrawalAmount)
            .build();

        // when
        TransactionDTO result = transactionService.createTransaction(withdrawal);
        AccountDTO resultAccount = accountService.getAccount(account1.getAccountId());

        // then
        assertNotNull(result);

        assertEquals("출금", result.getDescription());
        assertEquals(withdrawalAmount, result.getAmount());
        assertEquals(depositAmount, result.getBeforeBalance());
        assertEquals(withdrawalAmount, result.getAfterBalance());

        assertEquals(withdrawalAmount, resultAccount.getBalance());
        assertEquals(withdrawalAmount, resultAccount.getDailyTotalWithdrawal());
    }

    @Test
    @DisplayName("이체")
    void transferTransaction() {
        // given
        BigDecimal depositAmount = new BigDecimal("10000"); // 1만원

        TransactionCreateDTO deposit = TransactionCreateDTO.builder()
            .type(TransactionType.DEPOSIT)
            .accountNo(account1.getAccountNo())
            .amount(depositAmount)
            .build();
        transactionService.createTransaction(deposit);

        BigDecimal transferAmount = new BigDecimal("3000");

        TransactionCreateDTO transfer = TransactionCreateDTO.builder()
            .type(TransactionType.TRANSFER)
            .accountNo(account1.getAccountNo())
            .amount(transferAmount)
            .otherAccountNo(account2.getAccountNo())
            .build();

        // when
        TransactionDTO result = transactionService.createTransaction(transfer);
        AccountDTO withdrawalAccount = accountService.getAccount(account1.getAccountId());
        AccountDTO depositAccount = accountService.getAccount(account2.getAccountId());

        // then
        assertNotNull(result);

        BigDecimal transferResultAmount = transferAmount.multiply(TRANSFER_FEE)
            .setScale(0, RoundingMode.FLOOR);

        assertEquals(transferResultAmount, result.getAmount());
        assertEquals(depositAmount, result.getBeforeBalance());
        assertEquals(depositAmount.subtract(transferResultAmount), result.getAfterBalance());

        assertEquals(transferAmount, depositAccount.getBalance());
        assertEquals(transferAmount, withdrawalAccount.getDailyTotalTransfer());
    }


    @Test
    @DisplayName("출금 한도 초과 예외")
    void withdrawalTransactionException1() {
        // given
        BigDecimal depositAmount = new BigDecimal("1100000"); // 110만원

        TransactionCreateDTO deposit = TransactionCreateDTO.builder()
            .type(TransactionType.DEPOSIT)
            .accountNo(account1.getAccountNo())
            .amount(depositAmount)
            .build();
        transactionService.createTransaction(deposit);

        BigDecimal withdrawalAmount = new BigDecimal("1050000"); // 105만원

        TransactionCreateDTO overDailyLimit = TransactionCreateDTO.builder()
            .type(TransactionType.WITHDRAWAL)
            .accountNo(account1.getAccountNo())
            .amount(withdrawalAmount)
            .build();

        // when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
            () -> transactionService.createTransaction(overDailyLimit));

        // then
        assertEquals(ErrorCode.OVER_DAILY_WITHDRAWAL_LIMIT, exception.getErrorCode());

    }

    @Test
    @DisplayName("출금 잔액 부족 예외")
    void withdrawalTransactionException2() {
        // given
        BigDecimal depositAmount = new BigDecimal("500000"); // 50만원

        TransactionCreateDTO deposit = TransactionCreateDTO.builder()
            .type(TransactionType.DEPOSIT)
            .accountNo(account1.getAccountNo())
            .amount(depositAmount)
            .build();
        transactionService.createTransaction(deposit);

        BigDecimal withdrawalAmount = new BigDecimal("600000"); // 60만원

        TransactionCreateDTO overBalance = TransactionCreateDTO.builder()
            .type(TransactionType.WITHDRAWAL)
            .accountNo(account1.getAccountNo())
            .amount(withdrawalAmount)
            .build();

        // when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class,
            () -> transactionService.createTransaction(overBalance));

        // then
        assertEquals(ErrorCode.INSUFFICIENT_ACCOUNT_BALANCE, exception.getErrorCode());

    }
}