package com.coconet.articleservice.service;

import com.coconet.articleservice.client.MemberClient;
import com.coconet.articleservice.common.response.Response;
import com.coconet.articleservice.dto.*;
import com.coconet.articleservice.entity.*;
import com.coconet.articleservice.entity.enums.ArticleType;
import com.coconet.articleservice.entity.enums.EstimatedDuration;
import com.coconet.articleservice.entity.enums.MeetingType;
import com.coconet.articleservice.entity.member.MemberResponse;
import com.coconet.articleservice.entity.member.MemberRoleResponse;
import com.coconet.articleservice.repository.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final RoleRepository roleRepository;
    private final ArticleRoleRepository articleRoleRepository;
    private final TechStackRepository techStackRepository;
    private final ArticleStackRepository articleStackRepository;
    private final CommentRepository commentRepository;
    private final EntityManager em;
    private final BookmarkRepository bookmarkRepository;
    private final MemberClient memberClient;

    public ArticleResponseDto createArticle(ArticleCreateRequestDto request, UUID memberUUID) {
        ArticleEntity articleEntitye = ArticleEntity.builder()
                .articleUUID(UUID.randomUUID())
                .title(request.getTitle())
                .content(request.getContent())
                .plannedStartAt(request.getPlannedStartAt().atTime(LocalTime.MAX))
                .expiredAt(request.getExpiredAt().atTime(LocalTime.MAX))
                .estimatedDuration(request.getEstimatedDuration().name())
                .viewCount(0)
                .bookmarkCount(0)
                .articleType(request.getArticleType().name())
                .status((byte) 1)
                .meetingType(request.getMeetingType().name())
                .memberUUID(memberUUID)
                .build();

        ArticleEntity savedArticle = articleRepository.save(articleEntitye);

        List<ArticleRoleEntity> articleRoleEntityList = request.getRoles()
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

        List<ArticleStackEntity> articleStackEntityList = request.getStacks()
                .stream()
                .map(articleStackDto -> {
                    ArticleStackEntity articleStackEntity = ArticleStackEntity.builder()
                            .article(savedArticle)
                            .techStack(techStackRepository.findByName(articleStackDto).orElseThrow(RuntimeException::new))
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build();
                    return articleStackRepository.save(articleStackEntity);
                }).toList();

        em.clear();
        em.flush();

        articleEntityToArticleResponse(savedArticle);

        return formDtoToResponseDto(articleRepository.getArticle(savedArticle.getArticleUUID()));
    }

    public ArticleResponseDto getArticle(String articleUUID){
        ArticleFormDto articleFormDto = articleRepository.getArticle(UUID.fromString(articleUUID));
        return formDtoToResponseDto(articleFormDto);
    }

    public Page<ArticleResponseDto> getArticles(ArticleFilterDto condition, Pageable pageable){


        List<RoleEntity> selectedRoles = roleRepository.getRoles(condition.getRoles());
        List<TechStackEntity> selectedStacks = techStackRepository.getTechStacks(condition.getStacks());

        String articleType = null;
        if (condition.getArticleType() != null){
            articleType = ArticleType.valueOf(condition.getArticleType().name()).toString();
        }
        String meetingType = null;
        if (condition.getMeetingType() != null){
            meetingType = MeetingType.valueOf(condition.getMeetingType().name()).toString();
        }

        Page<ArticleFormDto> filteredArticles = articleRepository.getArticles(selectedRoles, selectedStacks,
                condition.getKeyword(), articleType, meetingType, condition.isBookmark(), pageable);


        return filteredArticles.map(this::formDtoToResponseDto);
    }

    public ArticleResponseDto updateArticle(ArticleRequestDto articleRequestDto, UUID memberUUID){
        ArticleEntity article = articleRepository.findByArticleUUID(articleRequestDto.getArticleUUID())
                .orElseThrow(() -> new IllegalArgumentException("Not found article"));

        article.changeTitle(articleRequestDto.getTitle());
        article.changeContent(articleRequestDto.getContent());
        article.changPlannedStartAt(articleRequestDto.getPlannedStartAt().atTime(LocalTime.MAX));
        article.changeEstDuration(articleRequestDto.getEstimatedDuration().name());
        article.changeExpiredAt(articleRequestDto.getExpiredAt().atTime(LocalTime.MAX));
        article.changeMeetingType(articleRequestDto.getMeetingType().name());
        article.changeArticleType(articleRequestDto.getArticleType().name());

        updateRoles(articleRequestDto.getRoles(), article);
        updateStacks(articleRequestDto.getStacks(), article);

        ArticleFormDto returnArticle = articleRepository.getArticle(article.getArticleUUID());

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

    public List<ArticleResponseDto> getSuggestions(UUID memberUUID){
        Response<MemberResponse> memberInfo = memberClient.getMemberInfo(memberUUID);
        MemberResponse memberResponse = memberClient.getMemberInfo(memberUUID).getData();

        List<String> roles = memberResponse.getRoles().stream()
                .map(MemberRoleResponse::getName)
                .toList();
        List<RoleEntity> memberRoles = roleRepository.getRoles(roles);

        List<String> stacks = memberResponse.getStacks().stream()
                .map(Objects::toString)
                .toList();
        List<TechStackEntity> memberStacks = techStackRepository.getTechStacks(stacks);

        List<ArticleFormDto> suggestions = articleRepository.getSuggestions(memberRoles, memberStacks);

        return suggestions.stream()
                .sorted(Comparator.comparingInt(suggestion ->
                        calculatePriority(suggestion, memberRoles, memberStacks)))
                .map(this::formDtoToResponseDto)
                .toList();
    }

    public List<ArticleResponseDto> getPopularPosts(){
        return articleRepository.getPopularPosts().stream()
                .map(this::formDtoToResponseDto)
                .toList();
    }

    int calculatePriority(
            ArticleFormDto suggestion,
            List<RoleEntity> memberRoles,
            List<TechStackEntity> memberStacks
    ) {

        // Check if there is any matching roles between the suggestion and user roles
        boolean hasRole = memberRoles.stream()
                .anyMatch(role -> suggestion.getRoles().stream()
                        .map(ArticleRoleDto::getRoleName)
                        .anyMatch(roleName -> roleName.equals(role.getName())));

        // Check the number of matching stacks between the suggestion and user stacks
        long stackCount = memberStacks.stream()
                .filter(techStack -> suggestion.getStacks().stream()
                        .map(ArticleStackDto::getStackName)
                        .anyMatch(stackName -> stackName.equals(techStack.getName())))
                .count();

        if (hasRole && stackCount >= 2){
            return 1;
        } else if (stackCount >= 2) {
            return 2;
        } else if (hasRole){
            return 3;
        }
        return 0;
    }

    public String deleteArticle(UUID articleUUID, UUID memberUUID){
        ArticleEntity article = articleRepository.findByArticleUUID(articleUUID)
                .orElseThrow();

        // TODO refactoring about member-service connecting
        if (article.getMemberUUID().equals(memberUUID)){
            article.changeStatus((byte)0);
            return "Successfully deleted";
        }else{
            return "Only the author can delete the article";
        }
    }

    /**
     *  comment
     */
    public CommentResponseDto writeComment(CommentRequestDto request, UUID articleUUID, UUID memberUUID) {

        CommentEntity commentEntity = CommentEntity.builder()
                .content(request.getContent())
                .memberUUID(memberUUID)
                .article(articleRepository.findByArticleUUID(articleUUID)
                        .orElseThrow(() -> new IllegalArgumentException("Not Found Article")))
                .build();
        commentRepository.save(commentEntity);

        return CommentResponseDto.builder()
                .content(commentEntity.getContent())
                .createdAt(commentEntity.getCreatedAt())
                .updatedAt(commentEntity.getUpdatedAt())
                .memberUUID(commentEntity.getMemberUUID())
                .build();
    }

    public CommentResponseDto updateComment(Long commnetId, CommentRequestDto commentDto, UUID memberUUID) {

        CommentEntity commentEntity = commentRepository.findById(commnetId).orElseThrow(() -> new IllegalArgumentException("Not Found Comment"));

        if(commentEntity.getMemberUUID().equals(memberUUID)){
            // update
            commentEntity.changeContent(commentDto.getContent());
        }else {
            throw new IllegalArgumentException("Only commenter can update the Comment.");
        }

        return CommentResponseDto.builder()
                .content(commentEntity.getContent())
                .createdAt(commentEntity.getCreatedAt())
                .updatedAt(commentEntity.getUpdatedAt())
                .memberUUID(commentEntity.getMemberUUID())
                .build();
    }

    public void deleteComment(Long commentId, UUID memberUUID) {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found Comment"));

        if(commentEntity.getMemberUUID().equals(memberUUID)){
            commentRepository.delete(commentEntity);
        }else {
            throw new IllegalArgumentException("Only commenter can delete the Comment.");
        }
    }


    public BookmarkResponse updateBookmark(UUID articleUUID, UUID memberUUID) {
        ArticleEntity article = articleRepository.findByArticleUUID(articleUUID).orElseThrow();

        Optional<BookmarkEntity> bookmark = bookmarkRepository.findByArticleIdAndMemberUUID(article.getId(), memberUUID);

        if(bookmark.isPresent()) {
            bookmarkRepository.delete(bookmark.get());
            return null;
        }

//        article.setBookmark();
//        articleRepository.save(article);

        BookmarkEntity newBookmark = BookmarkEntity.builder()
                .articleId(article.getId())
                .memberUUID(memberUUID)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        BookmarkEntity responseBookmark = bookmarkRepository.save(newBookmark);
        ArticleEntity responseArticle = articleRepository.findById(responseBookmark.getArticleId()).orElseThrow();

        return BookmarkResponse.builder()
                .title(responseArticle.getTitle())
                .articleUUID(responseArticle.getArticleUUID())
                .plannedStartAt(responseArticle.getPlannedStartAt())
                .expiredAt(responseArticle.getExpiredAt())
                .build();
    }

    public List<BookmarkResponse> getBookmarks(UUID memberUUID) {
        List<BookmarkEntity> bookmarkList = bookmarkRepository.findAllByMemberUUID(memberUUID);
        List<BookmarkResponse> response = new ArrayList<>();

        for (BookmarkEntity bookmarkEntity : bookmarkList) {
            ArticleEntity article = articleRepository.findById(bookmarkEntity.getArticleId()).orElseThrow();
            response.add(BookmarkResponse.builder()
                    .articleUUID(article.getArticleUUID())
                    .title(article.getTitle())
                    .plannedStartAt(article.getPlannedStartAt())
                    .expiredAt(article.getExpiredAt())
                    .build());
        }

        return response;
    }

    /**
     * UTILS
     */

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
                .memberUUID(articleFormDto.getMemberUUID())
                .roles(articleFormDto.getRoles())
                .stacks(articleFormDto.getStacks())
                .comments(articleFormDto.getComments())
                .build();
    }

    private static void articleEntityToArticleResponse(ArticleEntity savedArticle) {
        ArticleResponseDto articleResponseDto = ArticleResponseDto.builder()
                .articleUUID(savedArticle.getArticleUUID())
                .title(savedArticle.getTitle())
                .content(savedArticle.getContent())
                .createdAt(savedArticle.getCreatedAt())
                .updateAt(savedArticle.getUpdatedAt())
                .plannedStartAt(savedArticle.getPlannedStartAt())
                .expiredAt(savedArticle.getExpiredAt())
                .estimatedDuration(EstimatedDuration.valueOf(savedArticle.getEstimatedDuration()))
                .viewCount(savedArticle.getViewCount())
                .bookmarkCount(savedArticle.getBookmarkCount())
                .articleType(ArticleType.valueOf(savedArticle.getArticleType()))
                .status(savedArticle.getStatus())
                .meetingType(MeetingType.valueOf(savedArticle.getMeetingType()))
                .memberUUID(savedArticle.getMemberUUID())
                .roles(getArticleRoleDtos(savedArticle))
                .stacks(getArticleStackDtos(savedArticle))
                .comments(getCommentResponseDtos(savedArticle))
                .build();
    }


    private static List<ArticleRoleDto> getArticleRoleDtos(ArticleEntity savedArticle) {
        List<ArticleRoleDto> articleRoleDtoList = savedArticle.getArticleRoles().stream()
                .map(articleRoleEntity -> {
                    return ArticleRoleDto.builder()
                            .roleName(articleRoleEntity.getRole().getName())
                            .participant(articleRoleEntity.getParticipant())
                            .build();
                }).collect(Collectors.toList());
        return articleRoleDtoList;
    }

    private static List<ArticleStackDto> getArticleStackDtos(ArticleEntity savedArticle) {
        List<ArticleStackDto> articleStackDtoList = savedArticle.getArticleStacks().stream()
                .map(articleStackEntity -> {
                    return ArticleStackDto.builder()
                            .stackName(articleStackEntity.getTechStack().getName())
                            .category(articleStackEntity.getTechStack().getCategory())
                            .image(articleStackEntity.getTechStack().getImage())
                            .build();
                }).collect(Collectors.toList());
        return articleStackDtoList;
    }

    private static List<CommentResponseDto> getCommentResponseDtos(ArticleEntity articleEntity) {
        List<CommentResponseDto> commentResponseDtoList = articleEntity.getComments().stream()
                .map(comment -> {
                    return CommentResponseDto.builder()
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .updatedAt(comment.getUpdatedAt())
                            .memberUUID(comment.getMemberUUID())
                            .build();
                }).collect(Collectors.toList());
        return commentResponseDtoList;
    }
}
