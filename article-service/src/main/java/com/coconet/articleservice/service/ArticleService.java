package com.coconet.articleservice.service;

import com.coconet.articleservice.dto.*;
import com.coconet.articleservice.entity.*;
import com.coconet.articleservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final RoleRepository roleRepository;
    private final ArticleRoleRepository articleRoleRepository;
    private final TechStackRepository techStackRepository;
    private final ArticleStackRepository articleStackRepository;

    public ArticleResponseDto createArticle(ArticleRequestDto request) {

        MemberEntity member = new MemberEntity(1L, "test@Test.com", "tester");

        ArticleEntity articleentity = ArticleEntity.builder()
                .articleUUID(UUID.randomUUID())
                .title(request.getTitle())
                .content(request.getContent())
                .plannedStartAt(request.getPlannedStartAt().atTime(LocalTime.MAX))
                .expiredAt(request.getExpiredAt().atTime(LocalTime.MAX))
                .estimatedDuration(request.getEstimatedDuration())
                .viewCount(0)
                .bookmarkCount(0)
                .articleType(request.getArticleType())
                .status((byte) 1)
                .meetingType(request.getMeetingType())
                .member(member)
                .build();
        ArticleEntity savedArticle = articleRepository.save(articleentity);

        List<ArticleRoleEntity> articleRoleEntityList = request.getArticleRoleDtos()
                .stream()
                .map(articleRoleDto -> {
                    ArticleRoleEntity articleRoleEntity = ArticleRoleEntity.builder()
                            .article(savedArticle)
                            .role(roleRepository.findByName(articleRoleDto.getRoleName()).orElseThrow(RuntimeException::new))
                            .participant(articleRoleDto.getParticipant())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build();
                    return articleRoleRepository.save(articleRoleEntity);
                }).toList();

        List<ArticleStackEntity> articleStackEntityList = request.getArticleStackDtos()
                .stream()
                .map(articleStackDto -> {
                    ArticleStackEntity articleStackEntity = ArticleStackEntity.builder()
                            .article(savedArticle)
                            .techStack(techStackRepository.findByName(articleStackDto.getStackName()).orElseThrow(RuntimeException::new))
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build();
                    return articleStackRepository.save(articleStackEntity);
                }).toList();

        return formDtoToResponseDto(articleRepository.getArticle(savedArticle.getArticleUUID().toString()));

    }

    public ArticleResponseDto getArticle(String articleUUID){
        ArticleFormDto articleFormDto = articleRepository.getArticle(articleUUID);
        return formDtoToResponseDto(articleFormDto);
    }

    public Page<ArticleResponseDto> getArticles(String keyword, Pageable pageable){
        Page<ArticleFormDto> articleFormDtos = articleRepository.getArticles(keyword, pageable);
        Page<ArticleResponseDto> articleResponseDtos = articleFormDtos.map(articleFormDto ->
                formDtoToResponseDto(articleFormDto)
        );
        return  articleResponseDtos;
    }

    public ArticleResponseDto updateArticle(ArticleRequestDto articleRequestDto){
        ArticleEntity article = articleRepository.findByArticleUUID(UUID.fromString(articleRequestDto.getArticleUUID()))
                .orElseThrow(() -> new IllegalArgumentException("Not found article"));

        article.changeTitle(articleRequestDto.getTitle());
        article.changeContent(articleRequestDto.getContent());
        article.changPlannedStartAt(articleRequestDto.getPlannedStartAt().atTime(LocalTime.MAX));
        article.changeEstDuration(articleRequestDto.getEstimatedDuration());
        article.changeExpiredAt(articleRequestDto.getExpiredAt().atTime(LocalTime.MAX));
        article.changeMeetingType(articleRequestDto.getMeetingType());
        article.changeArticleType(articleRequestDto.getArticleType());

        updateRoles(articleRequestDto.getArticleRoleDtos(), article);
        updateStacks(articleRequestDto.getArticleStackDtos(), article);

        ArticleFormDto returnArticle = articleRepository.getArticle(article.getArticleUUID().toString());
        return formDtoToResponseDto(returnArticle);
    }

    public void updateStacks(List<ArticleStackDto> requestedStackDtos, ArticleEntity article){
        List<String> requestedStackNames = requestedStackDtos.stream()
                .map(ArticleStackDto::getStackName)
                .toList();

        List<ArticleStackEntity> currentArticleStacks = articleStackRepository.getArticleStacks(article);
        List<TechStackEntity> requestedStacks = articleStackRepository.getArticleStacksNameIn(requestedStackNames);

        List<String> currentArticleStackNames = currentArticleStacks.stream()
                .map(stack -> stack.getTechStack().getName())
                .toList();

        List<TechStackEntity> stacksToAdd = requestedStacks.stream()
                .filter(stack -> !currentArticleStackNames.contains(stack.getName()))
                .toList();

        List<ArticleStackEntity> stacksToRemove = currentArticleStacks.stream()
                .filter(stack -> !requestedStackNames.contains(stack.getTechStack().getName()))
                .toList();

        List<ArticleStackEntity> articleStacks = stacksToAdd.stream()
                .map(stack -> new ArticleStackEntity(article, stack))
                .toList();

        articleStackRepository.saveAll(articleStacks);
        articleStackRepository.deleteAllInBatch(stacksToRemove);
    }

    public void updateRoles(List<ArticleRoleDto> requestedRoles, ArticleEntity article){
        // Get current article's role list
        List<ArticleRoleEntity> currentArticleRoles = articleRoleRepository.getArticleRoles(article);

        // update article's roles
        List<ArticleRoleEntity> rolesToAdd = new ArrayList<>();
        List<ArticleRoleEntity> rolesToChange = new ArrayList<>();
        for (ArticleRoleDto req : requestedRoles){
            boolean roleExists = false;
            for (ArticleRoleEntity curr : currentArticleRoles){
                if (curr.getRole().getName().equals(req.getRoleName())){
                    roleExists = true;
                    if(!curr.getParticipant().equals(req.getParticipant())){
                        curr.changeParticipant(req.getParticipant());
                        rolesToChange.add(curr);
                    }
                    break;
                }
            }
            if (!roleExists){
                RoleEntity role = roleRepository.findByName(req.getRoleName())
                        .orElseThrow(() -> new IllegalArgumentException("Not found role"));
                rolesToAdd.add(
                        ArticleRoleEntity.builder()
                                .article(article)
                                .role(role)
                                .participant(req.getParticipant())
                                .build()
                );
            }
        }
        articleRoleRepository.saveAll(rolesToAdd);
        articleRoleRepository.saveAll(rolesToChange);

        List<Long> rolesToDelete = currentArticleRoles.stream()
                .filter(curr -> requestedRoles.stream()
                        .noneMatch(req -> req.getRoleName().equals(curr.getRole().getName())))
                .map(ArticleRoleEntity::getId)
                .toList();

        if (!rolesToDelete.isEmpty()){
            articleRoleRepository.deleteArticleRolesIn(rolesToDelete);
        }
    }

    public String deleteArticle(String articleUUID, String memberUUID){
        ArticleEntity article = articleRepository.findByArticleUUID(UUID.fromString(articleUUID))
                .orElseThrow();
        if (article.getMember().getMemberUUID().equals(UUID.fromString(memberUUID))){
            articleRepository.deleteById(article.getId());
            return "Successfully deleted";
        }else{
            return "Only the author can delete the article";
        }
    }

    private ArticleResponseDto formDtoToResponseDto(ArticleFormDto articleFormDto){
        return  ArticleResponseDto.builder()
                .articleUUID(articleFormDto.getArticleUUID())
                .title(articleFormDto.getTitle())
                .content(articleFormDto.getContent())
                .createdAt(articleFormDto.getCreatedAt())
                .updateAt(articleFormDto.getUpdateAt())
                .plannedStartAt(articleFormDto.getPlannedStartAt())
                .expiredAt(articleFormDto.getExpiredAt())
                .estimatedDuration(articleFormDto.getEstimatedDuration())
                .viewCount(articleFormDto.getViewCount())
                .bookmarkCount(articleFormDto.getBookmarkCount())
                .articleType(articleFormDto.getArticleType())
                .status(articleFormDto.getStatus())
                .meetingType(articleFormDto.getMeetingType())
                .author(articleFormDto.getAuthor())
                .articleRoleDtos(articleFormDto.getArticleRoleDtos())
                .articleStackDtos(articleFormDto.getArticleStackDtos())
                .build();
    }
}
