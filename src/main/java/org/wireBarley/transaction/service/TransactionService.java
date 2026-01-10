package org.wireBarley.transaction.service;

import static org.wireBarley.account.service.AccountService.BANK_CODE;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.wireBarley.account.entity.AccountEntity;
import org.wireBarley.account.repository.AccountRepository;
import org.wireBarley.common.exception.DataNotFoundException;
import org.wireBarley.common.exception.ErrorCode;
import org.wireBarley.common.exception.InvalidRequestException;
import org.wireBarley.transaction.domain.Direction;
import org.wireBarley.transaction.dto.TransactionCreateDTO;
import org.wireBarley.transaction.dto.TransactionDTO;
import org.wireBarley.transaction.dto.TransactionFilterDTO;
import org.wireBarley.transaction.entity.TransactionEntity;
import org.wireBarley.transaction.mapper.TransactionMapper;
import org.wireBarley.transaction.repository.TransactionRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

    public final TransactionRepository transactionRepository;

    public final TransactionMapper transactionMapper;

    public final AccountRepository accountRepository;

    private static final BigDecimal DAILY_WITHDRAWAL_LIMIT = new BigDecimal("1000000");
    private static final BigDecimal DAILY_TRANSFER_LIMIT = new BigDecimal("3000000");
    private static final BigDecimal TRANSFER_FEE = new BigDecimal("1.01");

    public TransactionDTO createTransaction(TransactionCreateDTO createDTO) {
        return switch (createDTO.getType()) {
            case DEPOSIT -> deposit(createDTO);
            case WITHDRAWAL -> withdrawal(createDTO);
            case TRANSFER -> transfer(createDTO);
        };
    }

    public Page<TransactionDTO> getTransactionList(TransactionFilterDTO filter) {

        // 유효하는 계좌인지 확인
        getAccountEntityByAccountNo(filter.getAccountNo());

        return transactionRepository.findByFilter(filter, filter.getPageRequest());
    }

    // 입금
    private TransactionDTO deposit(TransactionCreateDTO createDTO) {
        AccountEntity account = getAccountEntityByAccountNo(createDTO.getAccountNo());

        TransactionEntity entity = transactionMapper.toEntity(createDTO);
        entity.setDescription("입금");
        return deposit(entity, account);
    }

    // 입금 - db 조회를 최소화 위한 오버로딩
    private TransactionDTO deposit(TransactionEntity transactionEntity, AccountEntity account) {

        BigDecimal beforeBalance = account.getBalance();

        transactionEntity.setAccountId(account.getAccountId());
        transactionEntity.setDirection(Direction.IN);
        transactionEntity.setBeforeBalance(beforeBalance);
        transactionEntity.setAfterBalance(beforeBalance.add(transactionEntity.getAmount()));

        account.setBalance(beforeBalance.add(transactionEntity.getAmount()));

        return transactionMapper.toDTO(transactionRepository.save(transactionEntity));
    }

    // 출금
    private TransactionDTO withdrawal(TransactionCreateDTO createDTO) {
        AccountEntity account = getAccountEntityByAccountNo(createDTO.getAccountNo());

        BigDecimal totalWithdrawal = account.getDailyTotalWithdrawal().add(createDTO.getAmount());

        // 출금 한도 확인
        if (totalWithdrawal.compareTo(DAILY_WITHDRAWAL_LIMIT) > 0) {
            throw new InvalidRequestException(ErrorCode.OVER_DAILY_WITHDRAWAL_LIMIT);
        }

        // 일 한도 수정
        account.setDailyTotalWithdrawal(totalWithdrawal);

        TransactionEntity entity = transactionMapper.toEntity(createDTO);
        entity.setDescription("출금");

        return withdrawal(entity, account);
    }

    // 출금 - 재사용을 위한 오버로딩
    private TransactionDTO withdrawal(TransactionEntity transactionEntity, AccountEntity account) {

        // 출금 결과 잔액
        BigDecimal afterBalance = account.getBalance().subtract(transactionEntity.getAmount());

        // 잔액 확인
        if (afterBalance.signum() < 0) {
            throw new InvalidRequestException(ErrorCode.INSUFFICIENT_ACCOUNT_BALANCE);
        }

        transactionEntity.setBeforeBalance(account.getBalance());

        account.setBalance(afterBalance);

        transactionEntity.setAccountId(account.getAccountId());
        transactionEntity.setAfterBalance(afterBalance);
        transactionEntity.setDirection(Direction.OUT);

        return transactionMapper.toDTO(transactionRepository.save(transactionEntity));
    }

    // 이체
    private TransactionDTO transfer(TransactionCreateDTO createDTO) {

        if (createDTO.getAccountNo().equals(createDTO.getOtherAccountNo())) {
            throw new InvalidRequestException(ErrorCode.CAN_NOT_TRANSFER_THIS_ACCOUNT);
        }

        AccountEntity withdrawalAccount = getAccountEntityByAccountNo(createDTO.getAccountNo());

        // 이체 한도 확인
        BigDecimal totalTransfer = withdrawalAccount.getDailyTotalTransfer()
            .add(createDTO.getAmount());

        if (totalTransfer.compareTo(DAILY_TRANSFER_LIMIT) > 0) {
            throw new InvalidRequestException(ErrorCode.OVER_DAILY_TRANSFER_LIMIT);
        }
        withdrawalAccount.setDailyTotalTransfer(totalTransfer);

        // 출금 이체내역
        TransactionEntity withdrawalEntity = transactionMapper.toEntity(createDTO);
        if (!StringUtils.hasText(withdrawalEntity.getDescription())) {
            withdrawalEntity.setDescription(createDTO.getOtherAccountNo() + "_이체");
        }

        // 수수료 합산 후 출금
        withdrawalEntity.setAmount(createDTO.getAmount().multiply(TRANSFER_FEE));

        TransactionDTO withdrawalResult = withdrawal(withdrawalEntity, withdrawalAccount);

        // 입금 이체내역
        TransactionEntity depositEntity = transactionMapper.toDepositEntity(createDTO);
        depositEntity.setOtherBankCode(BANK_CODE);
        if (!StringUtils.hasText(depositEntity.getDescription())) {
            depositEntity.setDescription(createDTO.getAccountNo() + "_이체");
        }

        deposit(depositEntity, getAccountEntityByAccountNo(createDTO.getOtherAccountNo()));

        return withdrawalResult;
    }


    // account 데이터 조회
    private AccountEntity getAccountEntityByAccountNo(String accountNo) {
        // 존재 여부 확인
        AccountEntity entity = accountRepository.findByAccountNo(accountNo)
            .orElseThrow(() -> new DataNotFoundException(ErrorCode.ACCOUNT_DATA_NOT_FOUND));

        // 삭제 처리 확인
        if (entity.isDeleted()) {
            throw new DataNotFoundException(ErrorCode.ACCOUNT_DATA_NOT_FOUND);
        }
        return entity;
    }
}
