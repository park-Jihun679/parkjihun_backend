package org.wireBarley.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccountEntity is a Querydsl query type for AccountEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountEntity extends EntityPathBase<AccountEntity> {

    private static final long serialVersionUID = -651321352L;

    public static final QAccountEntity accountEntity = new QAccountEntity("accountEntity");

    public final ComparablePath<java.util.UUID> accountId = createComparable("accountId", java.util.UUID.class);

    public final StringPath accountName = createString("accountName");

    public final StringPath accountNo = createString("accountNo");

    public final NumberPath<java.math.BigDecimal> balance = createNumber("balance", java.math.BigDecimal.class);

    public final StringPath bankCode = createString("bankCode");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigDecimal> dailyTotalTransfer = createNumber("dailyTotalTransfer", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> dailyTotalWithdrawal = createNumber("dailyTotalWithdrawal", java.math.BigDecimal.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final DateTimePath<java.time.LocalDateTime> updatedDate = createDateTime("updatedDate", java.time.LocalDateTime.class);

    public QAccountEntity(String variable) {
        super(AccountEntity.class, forVariable(variable));
    }

    public QAccountEntity(Path<? extends AccountEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccountEntity(PathMetadata metadata) {
        super(AccountEntity.class, metadata);
    }

}

