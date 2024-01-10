package com.coconet.memberservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberStackEntity is a Querydsl query type for MemberStackEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberStackEntity extends EntityPathBase<MemberStackEntity> {

    private static final long serialVersionUID = -2079531729L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberStackEntity memberStackEntity = new QMemberStackEntity("memberStackEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMemberEntity member;

    public final QTechStackEntity techStack;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMemberStackEntity(String variable) {
        this(MemberStackEntity.class, forVariable(variable), INITS);
    }

    public QMemberStackEntity(Path<? extends MemberStackEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberStackEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberStackEntity(PathMetadata metadata, PathInits inits) {
        this(MemberStackEntity.class, metadata, inits);
    }

    public QMemberStackEntity(Class<? extends MemberStackEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMemberEntity(forProperty("member")) : null;
        this.techStack = inits.isInitialized("techStack") ? new QTechStackEntity(forProperty("techStack")) : null;
    }

}

