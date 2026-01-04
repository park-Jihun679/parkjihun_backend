package org.wireBarley.account.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import javax.security.auth.login.AccountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wireBarley.account.dto.AccountCreateDTO;
import org.wireBarley.account.dto.AccountDTO;
import org.wireBarley.account.entity.AccountEntity;
import org.wireBarley.account.mapper.AccountMapper;
import org.wireBarley.account.repository.AccountRepository;
import org.wireBarley.common.exception.DataNotFoundException;
import org.wireBarley.common.exception.DuplicatedDataException;
import org.wireBarley.common.exception.ErrorCode;
import org.wireBarley.common.exception.InvalidRequestException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountService {

    public static final String BANK_CODE = "0001";

    public final AccountRepository accountRepository;

    public final AccountMapper accountMapper;

    // 계좌 생성
    public AccountDTO createAccount(AccountCreateDTO createDTO) {

        // 계좌번호 지정 시 중복 체크
        if (!createDTO.getAccountNo().isEmpty()) {
            accountRepository.findByAccountNo(createDTO.getAccountNo()).ifPresent(
                account -> {
                    throw new DuplicatedDataException(ErrorCode.DUPLICATE_ACCOUNT);
                }
            );
        }

        AccountEntity entity = accountMapper.toEntity(createDTO);

        if (entity.getBankCode().isEmpty()) {
            entity.setBankCode(BANK_CODE);
        }

        // 계좌 번호 지정
        if (entity.getAccountNo().isEmpty()) {
            entity.setAccountNo(generateAccountNo());
        }

        // 계좌 이름 지정
        if (entity.getAccountName().isEmpty()) {
            entity.setAccountName(generateAccountName(entity.getAccountNo()));
        }


        return accountMapper.toDTO(accountRepository.saveAndFlush(entity));

    }

    // 계좌 조회
    public AccountDTO getAccount(UUID accountId) {
        // 존재 여부 확인
        AccountEntity entity = accountRepository.findById(accountId)
            .orElseThrow(() -> new DataNotFoundException(ErrorCode.COMMON_DATA_NOT_FOUND));

        // 삭제 여부 확인
        if (entity.isDeleted()) {
            throw new DataNotFoundException(ErrorCode.COMMON_DATA_NOT_FOUND);
        }

        return accountMapper.toDTO(entity);
    }

    // 계좌 삭제
    public AccountDTO deleteAccount(UUID accountId) {
        // 존재 여부 확인
        AccountEntity entity = accountRepository.findById(accountId)
            .orElseThrow(() -> new DataNotFoundException(ErrorCode.COMMON_DATA_NOT_FOUND));

        // 삭제 처리 확인
        if (entity.isDeleted()) {
            throw new InvalidRequestException(ErrorCode.ACCOUNT_ALREADY_DELETED);
        }

        if (entity.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new InvalidRequestException(ErrorCode.ACCOUNT_BALANCE_NOT_ZERO);
        }

        entity.setDeleted(true);

        return accountMapper.toDTO(entity);
    }

    // 계좌번호 생성
    private String generateAccountNo() {
        int random = ThreadLocalRandom.current().nextInt(0, 100);

        // 은행 코드 + 유닉스 타임스템프 + 랜덤2자리
        return BANK_CODE + System.currentTimeMillis() + String.format("%02d", random);
    }

    // 계좌번호 뒤 6자리 + "계좌" 로 계좌명 임의 생성
    private String generateAccountName(String accountNo) {
        return accountNo.substring(accountNo.length() - 6) + "_계좌";
    }


}
