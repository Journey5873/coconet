package com.coconet.articleservice.service;

import com.coconet.articleservice.common.errorcode.ErrorCode;
import com.coconet.articleservice.common.exception.ApiException;
import com.coconet.articleservice.converter.ApplicationConverter;
import com.coconet.articleservice.dto.ApplicationDto;
import com.coconet.articleservice.dto.ArticleRoleDto;
import com.coconet.articleservice.entity.ApplicationEntity;
import com.coconet.articleservice.entity.ArticleEntity;
import com.coconet.articleservice.entity.ArticleRoleEntity;
import com.coconet.articleservice.entity.RoleEntity;
import com.coconet.articleservice.entity.enums.ArticleType;
import com.coconet.articleservice.repository.ApplicationRepository;
import com.coconet.articleservice.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ArticleRepository articleRepository;

    public ApplicationDto apply(ApplicationDto applicationDto, UUID memberUUID) {
        validateMember(memberUUID);
        ArticleEntity retrivedArticleEntity = findByArticleUUID(applicationDto.getArticleUUID());

        if (retrivedArticleEntity.getMemberUUID().equals(memberUUID)) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "You cannot apply to your own post.");
        }

//        if (ArticleType.PROJECT.equals(retrivedArticleEntity.getArticleType())){
//            boolean isPositionValid = retrivedArticleEntity.getArticleRoles().stream()
//                    .map(ArticleRoleEntity::getRole)
//                    .map(RoleEntity::getName)
//                    .anyMatch(roleName -> roleName.equals(applicationDto.getApplicationPosition()));
//
//            if (!isPositionValid) {
//                throw new ApiException(ErrorCode.BAD_REQUEST, "Invalid application position for this post.");
//            }
//        }

        ApplicationEntity applicationEntity = ApplicationConverter.converterToEntity(memberUUID, retrivedArticleEntity);

        ApplicationEntity savedApplication = applicationRepository.save(applicationEntity);

        return ApplicationConverter.converterToDto(savedApplication);
    }

    public Page<ApplicationDto> getMyApplications(UUID memberUUID, Pageable pageable) {
        validateMember(memberUUID);
        Page<ApplicationEntity> myApplications = applicationRepository.findAllByMemberUUID(memberUUID, pageable);
        return myApplications.map(ApplicationConverter::converterToDto);
    }

    /**
     * UTILS
     */

    private void validateMember(UUID memberUUID) {
        if (memberUUID == null) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "You need to log in first.");
        }
    }

    private ArticleEntity findByArticleUUID(UUID articleUUID) {
        return articleRepository.findByArticleUUID(articleUUID)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "No Post found"));
    }
}
