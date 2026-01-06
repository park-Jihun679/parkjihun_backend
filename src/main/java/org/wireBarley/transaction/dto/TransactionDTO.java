package org.wireBarley.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.wireBarley.transaction.domain.Direction;

@Data
@Builder
public class TransactionDTO {
    private UUID accountId;

    private String accountNo;

    private Direction direction;

    private BigDecimal amount;

    private BigDecimal beforeBalance;

    private BigDecimal afterBalance;

    private String otherBankCode;

    private String otherAccountNo;

    private String description;

    private LocalDateTime createdDate;
}
