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
import java.util.List;

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
                .title(request.getTitle())
                .content(request.getContent())
                .plannedStartAt(request.getPlannedStartAt().atTime(23, 59, 59))
                .expiredAt(request.getExpiredAt().atTime(23, 59, 59))
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

        return buildArticleResponseDto(articleRepository.getArticle(savedArticle.getId()));

    }

    public ArticleResponseDto getArticle(Long articleId){
        ArticleFormDto articleFormDto = articleRepository.getArticle(articleId);
        return buildArticleResponseDto(articleFormDto);
    }

    public Page<ArticleResponseDto> getArticles(String keyword, Pageable pageable){
        Page<ArticleFormDto> articleFormDtos = articleRepository.getArticles(keyword, pageable);
        Page<ArticleResponseDto> articleResponseDtos = articleFormDtos.map(articleFormDto ->
                        buildArticleResponseDto(articleFormDto)
                );
        return  articleResponseDtos;
    }

    public ArticleResponseDto updateArticle(ArticleRoleDto articleRoleDto, Long articleId){
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Not found article"));


        return null;
    }

    public String deleteArticle(Long articleId, Long memberId){
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow();
        if (article.getMember().equals(memberId)){
            articleRepository.deleteById(articleId);
            return "Successfully deleted";
        }else{
            return "Only the author can delete the article";
        }
    }

    private ArticleResponseDto buildArticleResponseDto(ArticleFormDto articleFormDto){
        return  ArticleResponseDto.builder()
                .articleId(articleFormDto.getArticleId())
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
