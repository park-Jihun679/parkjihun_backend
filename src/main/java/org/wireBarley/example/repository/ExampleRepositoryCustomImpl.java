package org.wireBarley.example.repository;

import static org.wireBarley.example.entity.QExampleEntity.exampleEntity;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.wireBarley.example.dto.ExampleFilterDTO;
import org.wireBarley.example.entity.ExampleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@RequiredArgsConstructor
public class ExampleRepositoryCustomImpl implements
    ExampleRepositoryCustom {

  private final JPAQueryFactory queryFactory;


  @Override
  public Page<ExampleEntity> findByFilter(ExampleFilterDTO filterDTO, Pageable pageable) {

    List<ExampleEntity> entityList = queryFactory.selectFrom(exampleEntity)
        .where(
            codeContains(filterDTO.getExampleCode()),
            stringContains(filterDTO.getExampleString()),
            keywordContains(filterDTO.getKeyword())
        )
        .orderBy(getOrderSpecifiers(pageable.getSort()))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = queryFactory.select(exampleEntity.count())
        .from(exampleEntity)
        .where(
            codeContains(filterDTO.getExampleCode()),
            stringContains(filterDTO.getExampleString()),
            keywordContains(filterDTO.getKeyword())
        )
        .fetchOne();

    return new PageImpl<>(entityList, pageable, total != null ? total : 0);
  }

  private BooleanExpression codeContains(String exampleCode){
    return exampleCode != null ? exampleEntity.exampleCode.contains(exampleCode) : null;
  }

  private BooleanExpression stringContains(String exampleString){
    return exampleString != null ? exampleEntity.exampleString.contains(exampleString) : null;
  }

  private BooleanExpression keywordContains(String keyword){
    return keyword != null ? codeContains(keyword)
        .or(stringContains(keyword)) : null;
  }

  private OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {
    List<OrderSpecifier<?>> result = new ArrayList<>();

    if (sort != null) {
      for (Sort.Order order : sort) {
        Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
        result.add(new OrderSpecifier(direction,
            Expressions.path(Object.class, exampleEntity, order.getProperty())));
      }
    }

    result.add(new OrderSpecifier(Order.ASC,
        Expressions.path(Object.class, exampleEntity, "exampleCode")));

    return result.toArray(OrderSpecifier[]::new);
  }
}
