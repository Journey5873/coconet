package com.coconet.memberservice.repository;

import com.coconet.memberservice.common.errorcode.ErrorCode;
import com.coconet.memberservice.common.exception.ApiException;
import com.coconet.memberservice.dto.MemberInfoDto;
import com.coconet.memberservice.entity.MemberEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.swing.text.html.Option;
import java.util.Optional;
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

    @Override
    public Optional<MemberEntity> findByName(String name) {

        MemberEntity member = queryFactory.selectFrom(memberEntity)
                .where(memberEntity.isActivated.eq((byte) 1), memberEntity.name.eq(name))
                .fetchFirst();

        if(member == null)
            return Optional.empty();

        return Optional.of(member);
    }

    @Override
    public Optional<MemberEntity> findByEmail(String email) {
        MemberEntity member = queryFactory.selectFrom(memberEntity)
                .where(memberEntity.isActivated.eq((byte) 1), memberEntity.email.eq(email))
                .fetchFirst();

        if(member == null)
            return Optional.empty();

        return Optional.of(member);
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
