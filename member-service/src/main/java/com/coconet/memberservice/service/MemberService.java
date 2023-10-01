package com.coconet.memberservice.service;

import com.coconet.memberservice.entity.MemberEntity;
import com.coconet.memberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void updateName(MemberEntity member, String name){
        member.changeName(name);
        memberRepository.save(member);
    }

    public void updateCareer(MemberEntity member, String career){
        member.changeCareer(career);
        memberRepository.save(member);
    }

}
