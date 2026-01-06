package org.wireBarley.transaction.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTransactionEntity is a Querydsl query type for TransactionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTransactionEntity extends EntityPathBase<TransactionEntity> {

    private static final long serialVersionUID = -221867176L;

    public static final QTransactionEntity transactionEntity = new QTransactionEntity("transactionEntity");

    public final ComparablePath<java.util.UUID> accountId = createComparable("accountId", java.util.UUID.class);

    public final StringPath accountNo = createString("accountNo");

    public final NumberPath<java.math.BigDecimal> afterBalance = createNumber("afterBalance", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> beforeBalance = createNumber("beforeBalance", java.math.BigDecimal.class);

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final EnumPath<org.wireBarley.transaction.domain.Direction> direction = createEnum("direction", org.wireBarley.transaction.domain.Direction.class);

    public final StringPath otherAccountNo = createString("otherAccountNo");

    public final StringPath otherBankCode = createString("otherBankCode");

    public final ComparablePath<java.util.UUID> transactionId = createComparable("transactionId", java.util.UUID.class);

    public QTransactionEntity(String variable) {
        super(TransactionEntity.class, forVariable(variable));
    }

    public QTransactionEntity(Path<? extends TransactionEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTransactionEntity(PathMetadata metadata) {
        super(TransactionEntity.class, metadata);
    }

}

