package com.coconet.memberservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTechStackEntity is a Querydsl query type for TechStackEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTechStackEntity extends EntityPathBase<TechStackEntity> {

    private static final long serialVersionUID = -1580933325L;

    public static final QTechStackEntity techStackEntity = new QTechStackEntity("techStackEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath category = createString("category");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QTechStackEntity(String variable) {
        super(TechStackEntity.class, forVariable(variable));
    }

    public QTechStackEntity(Path<? extends TechStackEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTechStackEntity(PathMetadata metadata) {
        super(TechStackEntity.class, metadata);
    }

}

