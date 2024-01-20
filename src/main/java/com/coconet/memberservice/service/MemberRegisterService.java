package com.coconet.memberservice.service;

import com.coconet.memberservice.common.errorcode.ErrorCode;
import com.coconet.memberservice.common.exception.ApiException;
import com.coconet.memberservice.entity.*;
import com.coconet.memberservice.repository.MemberRoleRepository;
import com.coconet.memberservice.repository.MemberStackRepository;
import com.coconet.memberservice.repository.RoleRepository;
import com.coconet.memberservice.repository.TechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberRegisterService {
    private final RoleRepository roleRepository;
    private final TechStackRepository techStackRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final MemberStackRepository memberStackRepository;

    public List<String> addRoles(MemberEntity memberEntity, List<String> roles) {
        if (roles == null || roles.isEmpty()){
            throw new ApiException(ErrorCode.BAD_REQUEST, "You need to choose at least one role.");
        }

        List<RoleEntity> inputRoleEntities = roleRepository.findByNameIn(roles);
        if (inputRoleEntities.size() != roles.size()){
            throw new ApiException(ErrorCode.NOT_FOUND, "Some roles can not be found.");
        }

        List<MemberRoleEntity> memberRoleEntities = memberRoleRepository.saveAll(
                inputRoleEntities.stream()
                        .map(roleEntity -> new MemberRoleEntity(memberEntity, roleEntity))
                        .toList()
        );

        return memberRoleEntities.stream()
                .map(memberRoleEntity -> memberRoleEntity.getRole().getName())
                .toList();
    }

    public List<String> addStacks(MemberEntity memberEntity, List<String> stacks) {
        if (stacks == null || stacks.isEmpty()){
            throw new ApiException(ErrorCode.BAD_REQUEST, "You need to choose at least one stack.");
        }

        List<TechStackEntity> inputStackEntities = techStackRepository.findByNameIn(stacks);
        if (inputStackEntities.size() != stacks.size()){
            throw new ApiException(ErrorCode.NOT_FOUND, "Some tech stacks can not be found.");
        }

        List<MemberStackEntity> memberStackEntities = memberStackRepository.saveAll(
                inputStackEntities.stream()
                        .map(stackEntity -> new MemberStackEntity(memberEntity, stackEntity))
                        .toList()
        );

        return memberStackEntities.stream()
                .map(memberStackEntity -> memberStackEntity.getTechStack().getName())
                .toList();
    }

    public void updateRoles(MemberEntity member, List<String> requestedRoleNames) {

        // Get current roles
        List<MemberRoleEntity> currentRoles = memberRoleRepository.getAllRoles(member);

        List<RoleEntity> requestedRoles = roleRepository.findByNameIn(requestedRoleNames);

        List<String> currentRoleNames = currentRoles.stream()
                .map(role -> role.getRole().getName())
                .toList();

        // Identify new roles to add
        List<RoleEntity> rolesToAdd = requestedRoles.stream()
                .filter(role -> !currentRoleNames.contains(role.getName()))
                .toList();

        // Identify roles to remove
        List<MemberRoleEntity> rolesToRemove = currentRoles.stream()
                .filter(role -> !requestedRoleNames.contains(role.getRole().getName()))
                .toList();

        // Create MemberRoleEntity to add
        List<MemberRoleEntity> memberRoleEntities = rolesToAdd.stream()
                .map(role -> new MemberRoleEntity(member, role))
                .toList();

        // save
        memberRoleRepository.saveAll(memberRoleEntities);
        // remove
        memberRoleRepository.deleteAllInBatch(rolesToRemove);
    }

    public void updateStacks(MemberEntity member, List<String> requestedStackNames){

        // Get current stacks
        List<MemberStackEntity> currentStacks = memberStackRepository.getAllStacks(member);

        // Requested Stacks
        List<TechStackEntity> requestedStacks = techStackRepository.findByNameIn(requestedStackNames);

        List<String> currentStackNames = currentStacks.stream()
                .map(stack -> stack.getTechStack().getName())
                .toList();

        // Identify new stacks to add
        List<TechStackEntity> stacksToAdd = requestedStacks.stream()
                .filter(stack -> !currentStackNames.contains(stack.getName()))
                .toList();

        // Identify stacks to remove
        List<MemberStackEntity> stacksToRemove = currentStacks.stream()
                .filter(stack -> !requestedStackNames.contains(stack.getTechStack().getName()))
                .toList();

        // Create MemberStackEntity to add
        List<MemberStackEntity> memberStackEntities = stacksToAdd.stream()
                .map(stack -> new MemberStackEntity(member, stack))
                .toList();

        // Save new stacks
        memberStackRepository.saveAll(memberStackEntities);
        // Delete stacks
        memberStackRepository.deleteAllInBatch(stacksToRemove);
    }
}
