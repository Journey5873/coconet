package com.coconet.memberservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberRoleEntity is a Querydsl query type for MemberRoleEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberRoleEntity extends EntityPathBase<MemberRoleEntity> {

    private static final long serialVersionUID = 1768925909L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberRoleEntity memberRoleEntity = new QMemberRoleEntity("memberRoleEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMemberEntity member;

    public final QRoleEntity role;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMemberRoleEntity(String variable) {
        this(MemberRoleEntity.class, forVariable(variable), INITS);
    }

    public QMemberRoleEntity(Path<? extends MemberRoleEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberRoleEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberRoleEntity(PathMetadata metadata, PathInits inits) {
        this(MemberRoleEntity.class, metadata, inits);
    }

    public QMemberRoleEntity(Class<? extends MemberRoleEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMemberEntity(forProperty("member")) : null;
        this.role = inits.isInitialized("role") ? new QRoleEntity(forProperty("role")) : null;
    }

}

