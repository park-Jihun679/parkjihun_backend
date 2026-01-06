package org.wireBarley.transaction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.wireBarley.transaction.domain.Direction;

@Entity
@Table(name = "account_transaction")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transactionId;

    private UUID accountId;

    private String accountNo;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    @Column(precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(precision = 18, scale = 2)
    private BigDecimal beforeBalance;

    @Column(precision = 18, scale = 2)
    private BigDecimal afterBalance;

    private String otherBankCode;

    private String otherAccountNo;

    private String description;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

}
