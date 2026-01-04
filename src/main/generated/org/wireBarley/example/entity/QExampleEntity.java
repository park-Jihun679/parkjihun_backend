package org.wireBarley.example.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExampleEntity is a Querydsl query type for ExampleEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExampleEntity extends EntityPathBase<ExampleEntity> {

    private static final long serialVersionUID = 1550866008L;

    public static final QExampleEntity exampleEntity = new QExampleEntity("exampleEntity");

    public final org.wireBarley.common.entity.QBaseEntity _super = new org.wireBarley.common.entity.QBaseEntity(this);

    //inherited
    public final ComparablePath<java.util.UUID> createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath exampleCode = createString("exampleCode");

    public final ComparablePath<java.util.UUID> exampleId = createComparable("exampleId", java.util.UUID.class);

    public final StringPath exampleString = createString("exampleString");

    //inherited
    public final ComparablePath<java.util.UUID> updatedBy = _super.updatedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public QExampleEntity(String variable) {
        super(ExampleEntity.class, forVariable(variable));
    }

    public QExampleEntity(Path<? extends ExampleEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExampleEntity(PathMetadata metadata) {
        super(ExampleEntity.class, metadata);
    }

}

