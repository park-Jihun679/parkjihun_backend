package org.wireBarley.account.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDTO {

    private UUID accountId;

    private String accountNo;

    private String accountName;

    private String bankCode;

    private BigDecimal balance;

    private BigDecimal dailyTotalWithdrawal;

    private BigDecimal dailyTotalTransfer;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private boolean isDeleted;
}
