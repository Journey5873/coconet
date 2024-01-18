package com.coconet.memberservice.service;

import com.coconet.memberservice.entity.MemberEntity;
import com.coconet.memberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberValidationService {
    private final MemberRepository memberRepository;

    public boolean existMember(String email) {
        List<MemberEntity> members = memberRepository.findAllByEmail(email);

        for(MemberEntity member: members) {
            if(member.getIsActivated() == 1) return true;
        }

        return false;
    }
}
