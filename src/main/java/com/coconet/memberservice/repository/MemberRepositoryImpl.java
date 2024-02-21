package com.coconet.memberservice.repository;

import com.coconet.memberservice.common.errorcode.ErrorCode;
import com.coconet.memberservice.common.exception.ApiException;
import com.coconet.memberservice.converter.ImageConverter;
import com.coconet.memberservice.converter.MemberConverter;
import com.coconet.memberservice.dto.MemberResponseDto;
import com.coconet.memberservice.dto.client.MemberClientDto;
import com.coconet.memberservice.dto.client.QMemberClientDto;
import com.coconet.memberservice.entity.MemberEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import static com.coconet.memberservice.entity.QMemberEntity.*;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public MemberResponseDto getUserInfo(UUID memberUUID) {

        MemberEntity member = queryFactory.selectFrom(memberEntity)
                .where(memberEntity.memberUUID.eq(memberUUID))
                .fetchOne();

        if (member == null){
            throw new ApiException(ErrorCode.NOT_FOUND, "No member found");
        }

        return MemberConverter.toResponseDto(member);
    }

    @Override
    public Optional<MemberEntity> findByName(String name) {

        MemberEntity member = queryFactory.selectFrom(memberEntity)
                .where(
                        memberEntity.isActivated.eq((byte) 1),
                        memberEntity.name.eq(name)
                )
                .fetchFirst();

        if(member == null){
            return Optional.empty();
        }

        return Optional.of(member);
    }

    @Override
    public Optional<MemberEntity> findByEmail(String email) {
        MemberEntity member = queryFactory.selectFrom(memberEntity)
                .where(
                        memberEntity.isActivated.eq((byte) 1),
                        memberEntity.email.eq(email)
                )
                .fetchFirst();

        if(member == null){
            return Optional.empty();
        }

        return Optional.of(member);
    }

    @Override
    public MemberClientDto clientMemberProfile(UUID memberUUID) {
        MemberClientDto memberClientDto = queryFactory.select(
                new QMemberClientDto(memberEntity.name, memberEntity.memberUUID, memberEntity.profileImage))
                .from(memberEntity)
                .where(memberEntity.memberUUID.eq(memberUUID),
                        memberEntity.isActivated.eq((byte) 1))
                .fetchFirst();

        if (memberClientDto == null) {
            throw new ApiException(ErrorCode.NOT_FOUND, "No member found");
        }

        return memberClientDto;
    }
}
