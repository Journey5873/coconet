package com.coconet.memberservice.repository;

import com.coconet.memberservice.common.errorcode.ErrorCode;
import com.coconet.memberservice.common.exception.ApiException;
import com.coconet.memberservice.dto.MemberIdDto;
import com.coconet.memberservice.dto.MemberInfoDto;
import com.coconet.memberservice.dto.MemberRegisterRequestDto;
import com.coconet.memberservice.entity.MemberEntity;
import com.coconet.memberservice.entity.QMemberEntity;
import com.coconet.memberservice.entity.QMemberRoleEntity;
import com.coconet.memberservice.entity.QMemberStackEntity;
import com.coconet.memberservice.security.token.dto.TokenResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.coconet.memberservice.entity.QMemberEntity.*;
import static com.coconet.memberservice.entity.QMemberRoleEntity.memberRoleEntity;
import static com.coconet.memberservice.entity.QMemberStackEntity.memberStackEntity;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public MemberInfoDto getUserInfo(UUID memberUUID) {

        MemberEntity member = queryFactory.selectFrom(memberEntity)
                .leftJoin(memberEntity.memberRoles, memberRoleEntity)
                .leftJoin(memberEntity.memberStacks, memberStackEntity)
                .where(memberEntity.memberUUID.eq(memberUUID))
                .fetchOne();

        if (member == null){
            throw new ApiException(ErrorCode.NOT_FOUND, "No member found");
        }

        return entityToDto(member);
    }

    private MemberInfoDto entityToDto(MemberEntity member){
        return MemberInfoDto.builder()
                .name(member.getName())
                .career(Integer.parseInt(member.getCareer()))
                .profileImg(member.getProfileImage())
                .bio(member.getBio())
                .githubLink(member.getGithubLink())
                .blogLink(member.getBlogLink())
                .notionLink(member.getNotionLink())
                .roles(member.getMemberRoles().stream()
                        .map(memberRole -> memberRole.getRole().getName())
                        .toList())
                .stacks(member.getMemberStacks().stream()
                        .map(memberStack -> memberStack.getTechStack().getName())
                        .toList())
                .build();
    }
}
