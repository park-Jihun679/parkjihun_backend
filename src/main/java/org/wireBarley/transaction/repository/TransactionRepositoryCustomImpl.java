package org.wireBarley.transaction.repository;

import static com.querydsl.jpa.JPAExpressions.selectFrom;
import static org.wireBarley.transaction.entity.QTransactionEntity.transactionEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.wireBarley.transaction.domain.Direction;
import org.wireBarley.transaction.dto.TransactionDTO;
import org.wireBarley.transaction.dto.TransactionFilterDTO;
import org.wireBarley.transaction.entity.TransactionEntity;

@RequiredArgsConstructor
public class TransactionRepositoryCustomImpl implements TransactionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<TransactionDTO> findByFilter(TransactionFilterDTO filterDTO, Pageable pageable) {
        List<TransactionDTO> entityList = queryFactory.select(
                Projections.constructor(TransactionDTO.class,
                    transactionEntity.accountId,
                    transactionEntity.accountNo,
                    transactionEntity.direction,
                    transactionEntity.amount,
                    transactionEntity.beforeBalance,
                    transactionEntity.afterBalance,
                    transactionEntity.otherBankCode,
                    transactionEntity.otherAccountNo,
                    transactionEntity.description,
                    transactionEntity.createdDate
                ))
            .where(
                accountNoEquals(filterDTO.getAccountNo()),
                directionEquals(filterDTO.getDirection()),
                keywordContains(filterDTO.getDescriptionKeyword()),
                startDateGoe(filterDTO.getStartDate()),
                endDateLt(filterDTO.getEndDate())
            )
            .from(transactionEntity)
            .orderBy(transactionEntity.createdDate.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory.select(transactionEntity.count())
            .from(transactionEntity)
            .where(
                accountNoEquals(filterDTO.getAccountNo()),
                directionEquals(filterDTO.getDirection()),
                keywordContains(filterDTO.getDescriptionKeyword()),
                startDateGoe(filterDTO.getStartDate()),
                endDateLt(filterDTO.getEndDate())
            )
            .fetchOne();

        return new PageImpl<>(entityList, pageable, total != null ? total : 0);
    }

    private BooleanExpression accountNoEquals(String accountNo) {
        return accountNo != null ? transactionEntity.accountNo.eq(accountNo) : null;
    }

    private BooleanExpression directionEquals(Direction direction) {
        return direction != null ? transactionEntity.direction.eq(direction) : null;
    }

    private BooleanExpression keywordContains(String keyword) {
        return keyword != null ? transactionEntity.description.contains(keyword) : null;
    }

    private BooleanExpression startDateGoe(LocalDate startDate) {
        return startDate != null ? transactionEntity.createdDate.goe(startDate.atStartOfDay())
            : null;
    }

    private BooleanExpression endDateLt(LocalDate endDate) {
        return endDate != null ? transactionEntity.createdDate.lt(
            endDate.plusDays(1).atStartOfDay()) : null;
    }
}
