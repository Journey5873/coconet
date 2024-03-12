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
import org.springframework.util.FileCopyUtils;

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

    public void updateRoles(MemberEntity memberEntity, List<String> requestedRoleNames) {

        // Get current all member's roles
        List<MemberRoleEntity> currentRoleEntities = memberRoleRepository.findAllByMember(memberEntity);

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

    public void updateStacks(MemberEntity memberEntity, List<String> requestedStackNames){

        // Get current member's stacks
        List<MemberStackEntity> currentStackEntities = memberStackRepository.findAllByMember(memberEntity);

        // Retrieve requested stack entities
        List<TechStackEntity> requestedStackEntities = techStackRepository.findByNameIn(requestedStackNames);

        // Identify stacks to add
        List<TechStackEntity> stacksToAdd = requestedStackEntities.stream()
                .filter(requestedStack -> currentStackEntities.stream()
                        .noneMatch(currentStack -> currentStack.getTechStack().getName().equals(requestedStack.getName())))
                .toList();

        // Identify stacks to remove
        List<MemberStackEntity> stacksToRemove = currentStackEntities.stream()
                .filter(currentStack -> requestedStackEntities.stream()
                        .noneMatch(requestedStack -> requestedStack.getName().equals(currentStack.getTechStack().getName())))
                .toList();

        // Create MemberStackEntities and Save
        memberStackRepository.saveAll(
                stacksToAdd.stream()
                        .map(stackEntity -> new MemberStackEntity(memberEntity, stackEntity))
                        .toList()
        );

        // Remove MemberStackEntities
        memberStackRepository.deleteAllInBatch(stacksToRemove);
    }
}
