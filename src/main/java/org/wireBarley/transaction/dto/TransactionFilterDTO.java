package org.wireBarley.transaction.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.wireBarley.common.dto.BaseFilterDTO;
import org.wireBarley.transaction.domain.Direction;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class TransactionFilterDTO extends BaseFilterDTO {

    private String accountNo;

    private Direction direction;

    private String descriptionKeyword;

    private LocalDate startDate;
    private LocalDate endDate;
}
