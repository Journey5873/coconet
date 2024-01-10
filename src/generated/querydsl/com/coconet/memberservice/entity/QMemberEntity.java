package com.coconet.memberservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberEntity is a Querydsl query type for MemberEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberEntity extends EntityPathBase<MemberEntity> {

    private static final long serialVersionUID = -1486504769L;

    public static final QMemberEntity memberEntity = new QMemberEntity("memberEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath bio = createString("bio");

    public final StringPath blogLink = createString("blogLink");

    public final StringPath career = createString("career");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final StringPath githubLink = createString("githubLink");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Byte> isActivated = createNumber("isActivated", Byte.class);

    public final ListPath<MemberRoleEntity, QMemberRoleEntity> memberRoles = this.<MemberRoleEntity, QMemberRoleEntity>createList("memberRoles", MemberRoleEntity.class, QMemberRoleEntity.class, PathInits.DIRECT2);

    public final ListPath<MemberStackEntity, QMemberStackEntity> memberStacks = this.<MemberStackEntity, QMemberStackEntity>createList("memberStacks", MemberStackEntity.class, QMemberStackEntity.class, PathInits.DIRECT2);

    public final ComparablePath<java.util.UUID> memberUUID = createComparable("memberUUID", java.util.UUID.class);

    public final StringPath name = createString("name");

    public final StringPath notionLink = createString("notionLink");

    public final StringPath profileImage = createString("profileImage");

    public final EnumPath<com.coconet.memberservice.security.oauthModel.AuthProvider> provider = createEnum("provider", com.coconet.memberservice.security.oauthModel.AuthProvider.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMemberEntity(String variable) {
        super(MemberEntity.class, forVariable(variable));
    }

    public QMemberEntity(Path<? extends MemberEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberEntity(PathMetadata metadata) {
        super(MemberEntity.class, metadata);
    }

}

