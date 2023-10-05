package com.coconet.memberservice.service;

import com.coconet.memberservice.dto.UpdateRequestDto;
import com.coconet.memberservice.entity.MemberEntity;
import com.coconet.memberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public Optional<UpdateRequestDto> updateName(UpdateRequestDto request){
        Optional<MemberEntity> optionalMember = memberRepository.findById(request.getId());
        if (optionalMember.isPresent()) {
            MemberEntity member = optionalMember.get();
            member.changeName(request.getName());
            MemberEntity updatedMember = memberRepository.save(member);
            UpdateRequestDto updateRequestDto = new UpdateRequestDto(updatedMember.getName());
            return Optional.of(updateRequestDto);
        } else {
            return Optional.empty();
        }
    }

    public Optional<UpdateRequestDto> updateCareer(UpdateRequestDto request){
        Optional<MemberEntity> optionalMember = memberRepository.findById(request.getId());
        if (optionalMember.isPresent()) {
            MemberEntity member = optionalMember.get();
            member.changeCareer(request.getCareer());
            MemberEntity updatedMember = memberRepository.save(member);
            UpdateRequestDto updateRequestDto = new UpdateRequestDto(updatedMember.getCareer());
            return Optional.of(updateRequestDto);
        } else {
            return Optional.empty();
        }
    }
}
