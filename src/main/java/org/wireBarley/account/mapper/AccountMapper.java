package org.wireBarley.account.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.wireBarley.account.dto.AccountCreateDTO;
import org.wireBarley.account.dto.AccountDTO;
import org.wireBarley.account.entity.AccountEntity;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    public AccountEntity toEntity(AccountCreateDTO dto) {
        return AccountEntity.builder()
            .accountNo(dto.getAccountNo())
            .accountName(dto.getAccountName())
            .bankCode(dto.getBankCode())
            .balance(BigDecimal.ZERO)
            .dailyTotalWithdrawal(BigDecimal.ZERO)
            .dailyTotalTransfer(BigDecimal.ZERO)
            .build();
    }

    public AccountDTO toDTO(AccountEntity entity) {
        return AccountDTO.builder()
            .accountId(entity.getAccountId())
            .accountNo(entity.getAccountNo())
            .accountName(entity.getAccountName())
            .bankCode(entity.getBankCode())
            .balance(entity.getBalance().setScale(0, RoundingMode.FLOOR))
            .dailyTotalWithdrawal(entity.getDailyTotalWithdrawal().setScale(0, RoundingMode.FLOOR))
            .dailyTotalTransfer(entity.getDailyTotalTransfer().setScale(0, RoundingMode.FLOOR))
            .createdDate(entity.getCreatedDate())
            .isDeleted(entity.isDeleted())
            .build();
    }
}
