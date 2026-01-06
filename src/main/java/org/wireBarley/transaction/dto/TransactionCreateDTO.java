package org.wireBarley.transaction.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.wireBarley.transaction.domain.TransactionType;

@Builder
@Data
@AllArgsConstructor
public class TransactionCreateDTO {

    private TransactionType type;

    private String accountNo;

    private BigDecimal amount;

    private String otherBankCode;

    private String otherAccountNo;

    private String description;
}
