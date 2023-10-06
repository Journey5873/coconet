package com.coconet.memberservice.service;

import com.coconet.memberservice.dto.MemberStackResponseDto;
import com.coconet.memberservice.dto.UpdateStackRequestDto;
import com.coconet.memberservice.entity.MemberEntity;
import com.coconet.memberservice.entity.MemberStackEntity;
import com.coconet.memberservice.entity.TechStackEntity;
import com.coconet.memberservice.repository.MemberRepository;
import com.coconet.memberservice.repository.MemberStackRepository;
import com.coconet.memberservice.repository.TechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberStackService {
    private final MemberStackRepository memberStackRepository;
    private final MemberRepository memberRepository;
    private final TechStackRepository techStackRepository;

    public List<MemberStackResponseDto> getAllStacks(Long memberId){
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        List<MemberStackEntity> memberStacks = memberStackRepository.getAllByMember(member);
        List<MemberStackResponseDto> result = memberStacks.stream()
                .map(ms -> new MemberStackResponseDto(ms.getTechStack().getName(),
                                                        ms.getTechStack().getCategory(),
                                                        ms.getTechStack().getImage()))
                .collect(toList());
        return result;
    }

    public void updateMemberStacks(Long memberId, List<Long> stackIds) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        List<TechStackEntity> techStacks = techStackRepository.findAllById(stackIds);
        List<MemberStackEntity> memberStacks = new ArrayList<>();
        for (TechStackEntity techStack : techStacks) {
            MemberStackEntity memberStack = new MemberStackEntity(member, techStack);
            memberStacks.add(memberStack);
        }
        memberStackRepository.deleteByMember(member);
        memberStackRepository.saveAll(memberStacks);
    }
}
