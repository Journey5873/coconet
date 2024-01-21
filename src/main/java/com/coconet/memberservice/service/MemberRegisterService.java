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

    public void updateRoles(MemberEntity memberEntity, List<String> requestedRoleNames) {

        // Get current all member's roles
        List<MemberRoleEntity> currentRoleEntities = memberRoleRepository.getAllRoles(memberEntity);

        // Retrieve requested role entities
        List<RoleEntity> requestedRoleEntities = roleRepository.findByNameIn(requestedRoleNames);

        // Identify new roles to add
        List<RoleEntity> rolesToAdd = requestedRoleEntities.stream()
                .filter(requestedRole -> currentRoleEntities.stream()
                        .noneMatch(currentRole -> currentRole.getRole().getName().equals(requestedRole.getName())))
                .toList();

        // Identify roles to remove
        List<MemberRoleEntity> rolesToRemove = currentRoleEntities.stream()
                .filter(currentRole -> requestedRoleEntities.stream()
                        .noneMatch(requestedRole -> requestedRole.getName().equals(currentRole.getRole().getName())))
                .toList();

        // Create MemberRoleEntities and Save
        memberRoleRepository.saveAll(
                rolesToAdd.stream()
                        .map(roleEntity -> new MemberRoleEntity(memberEntity, roleEntity))
                        .toList()
        );

        // Remove MemberRoleEntities
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
