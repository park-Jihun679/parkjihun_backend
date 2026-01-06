package org.wireBarley.transaction.mapper;

import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.wireBarley.transaction.domain.Direction;
import org.wireBarley.transaction.dto.TransactionCreateDTO;
import org.wireBarley.transaction.dto.TransactionDTO;
import org.wireBarley.transaction.entity.TransactionEntity;

@Component
@RequiredArgsConstructor
public class TransactionMapper {

    public TransactionEntity toEntity(TransactionCreateDTO dto) {
        return TransactionEntity.builder()
            .accountNo(dto.getAccountNo())
            .amount(dto.getAmount())
            .otherBankCode(dto.getOtherBankCode())
            .otherAccountNo(dto.getOtherAccountNo())
            .description(dto.getDescription())
            .build();
    }

    public TransactionEntity toDepositEntity(TransactionCreateDTO dto) {
        return TransactionEntity.builder()
            .accountNo(dto.getOtherAccountNo())
            .direction(Direction.IN)
            .amount(dto.getAmount())
            .otherAccountNo(dto.getAccountNo())
            .description(dto.getDescription())
            .build();
    }

    public TransactionDTO toDTO(TransactionEntity entity) {
        return TransactionDTO.builder()
            .accountId(entity.getAccountId())
            .accountNo(entity.getAccountNo())
            .direction(entity.getDirection())
            .amount(entity.getAmount().setScale(0, RoundingMode.FLOOR))
            .beforeBalance(entity.getBeforeBalance().setScale(0, RoundingMode.FLOOR))
            .afterBalance(entity.getAfterBalance().setScale(0, RoundingMode.FLOOR))
            .otherBankCode(entity.getOtherBankCode())
            .otherAccountNo(entity.getOtherAccountNo())
            .description(entity.getDescription())
            .createdDate(entity.getCreatedDate())
            .build();
    }
}
