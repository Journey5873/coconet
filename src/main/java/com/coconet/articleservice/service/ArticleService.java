package com.coconet.articleservice.service;

import com.coconet.articleservice.client.MemberClient;
import com.coconet.articleservice.common.errorcode.ErrorCode;
import com.coconet.articleservice.common.exception.ApiException;
import com.coconet.articleservice.converter.ArticleConverter;
import com.coconet.articleservice.converter.CommentConverter;
import com.coconet.articleservice.dto.*;
import com.coconet.articleservice.dto.client.ChatClientResponseDto;
import com.coconet.articleservice.entity.*;
import com.coconet.articleservice.entity.enums.ArticleType;
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
        if (memberUUID == null){
            throw new ApiException(ErrorCode.BAD_REQUEST, "You need to login in first.");
        }

        ArticleEntity articleEntity = request.toEntity(memberUUID);
        ArticleEntity savedArticle = articleRepository.save(articleEntity);

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

        return ArticleConverter.convertToDto(articleEntity);
    }

    public ArticleResponseDto getArticle(String articleUUID){
        ArticleResponseDto articleResponseDto = articleRepository.getArticle(UUID.fromString(articleUUID));
        if (articleResponseDto == null){
            throw new ApiException(ErrorCode.NOT_FOUND, "No Post found");
        }

        return articleResponseDto;
    }

    public Page<ArticleResponseDto> getArticles(
            ArticleFilterDto condition,
            UUID memberUUID, Pageable pageable
    ){

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

        return articleRepository.getArticles(selectedRoles, selectedStacks,
                condition.getKeyword(), articleType, meetingType, condition.isBookmark(), memberUUID, pageable);
    }

    public Page<ArticleResponseDto> getMyArticles(UUID memberUUID, Pageable pageable){
        return articleRepository.getMyArticles(memberUUID, pageable);
    }

    public ArticleResponseDto updateArticle(ArticleRequestDto articleRequestDto, UUID memberUUID){
        ArticleEntity articleEntity = articleRepository.findByArticleUUID(articleRequestDto.getArticleUUID())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "No Post found"));

        if (!articleEntity.getMemberUUID().equals(memberUUID)){
            new ApiException(ErrorCode.FORBIDDEN_ERROR, "Only author can edit this post.");
        }

        articleEntity.updateArticle(articleRequestDto.getTitle(),
                articleRequestDto.getContent(),
                articleRequestDto.getPlannedStartAt().atTime(LocalTime.MAX),
                articleRequestDto.getExpiredAt().atTime(LocalTime.MAX),
                articleRequestDto.getEstimatedDuration().name(),
                articleRequestDto.getArticleType().name(),
                articleRequestDto.getMeetingType().name()
                );

        updateRoles(articleRequestDto.getRoles(), articleEntity);
        updateStacks(articleRequestDto.getStacks(), articleEntity);

        return ArticleConverter.convertToDto(articleEntity);
    }

    public void updateStacks(List<String> requestedStackNames, ArticleEntity article){

        // Get current all article's stacks
        List<ArticleStackEntity> currentStackEntities = articleStackRepository.getArticleStacks(article);

        // Retrieve requested stack entities
        List<TechStackEntity> requestedStackEntities = techStackRepository.findByNameIn(requestedStackNames);

        // Identify new stacks to add
        List<TechStackEntity> stacksToAdd = requestedStackEntities.stream()
                .filter(requestedStack -> currentStackEntities.stream()
                        .noneMatch(currentStack -> currentStack.getTechStack().getName().equals(requestedStack.getName())))
                .toList();

        // Identify stacks to Delete
        List<ArticleStackEntity> stacksToRemove = currentStackEntities.stream()
                .filter(currentStack -> requestedStackEntities.stream()
                        .noneMatch(requestedStack -> requestedStack.getName().equals(currentStack.getTechStack().getName())))
                .toList();

        // Create ArticleStackEntities and Save
        articleStackRepository.saveAll(
                stacksToAdd.stream()
                .map(stackEntity -> new ArticleStackEntity(article, stackEntity))
                .toList()
        );

        // Remove ArticleStackEntities
        articleStackRepository.deleteAllInBatch(stacksToRemove);
    }

    public void updateStacksOld(List<ArticleStackDto> requestedStackDtos, ArticleEntity article){

        List<String> requestedStackNames = requestedStackDtos.stream()
                .map(ArticleStackDto::getStackName)
                .toList();

        List<ArticleStackEntity> currentArticleStacks = articleStackRepository.getArticleStacks(article);
        List<TechStackEntity> requestedStacks = articleStackRepository.getArticleStackNamesIn(requestedStackNames);

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

    public void updateRoles(List<ArticleRoleDto> requestedRoles, ArticleEntity article) {

        // Get current ArticleRoleEntities
        List<ArticleRoleEntity> currentRoleEntities = articleRoleRepository.getArticleRoles(article);

        Map<String, ArticleRoleEntity> currentRolesMap = new HashMap<>();
        Map<String, ArticleRoleDto> requestedRolesMap = new HashMap<>();

        List<ArticleRoleEntity> rolesToUpdate = new ArrayList<>();
        List<Long> rolesToDelete = new ArrayList<>();

        currentRoleEntities.stream()
                .map(articleRole -> currentRolesMap.put(articleRole.getRole().getName(), articleRole))
                .toList();

        requestedRoles.stream()
                .map(articleRoleDto -> requestedRolesMap.put(articleRoleDto.getRoleName(), articleRoleDto))
                .toList();

        // Retrieve requested role entities
        List<RoleEntity> requestedRoleEntities = roleRepository.findByNameIn(List.copyOf(requestedRolesMap.keySet()));


        // Identify new roles to add & roles to update
        for (RoleEntity req : requestedRoleEntities){
            ArticleRoleEntity roleEntity = currentRolesMap.get(req.getName());

            if (roleEntity != null){
                if (!roleEntity.getParticipant().equals(requestedRolesMap.get(req.getName()).getParticipant())) {
                    roleEntity.changeParticipant(requestedRolesMap.get(req.getName()).getParticipant());
                    rolesToUpdate.add(roleEntity);
                }
            }else {
                rolesToUpdate.add(
                        ArticleRoleEntity.builder()
                                .article(article)
                                .role(req)
                                .participant(requestedRolesMap.get(req.getName()).getParticipant())
                                .build()
                );
            }
        }

        // Identify roles to remove
        for (ArticleRoleEntity role : currentRoleEntities) {
            if (requestedRoles.stream().noneMatch(req -> req.getRoleName().equals(role.getRole().getName()))) {
                rolesToDelete.add(role.getId());
            }
        }

        articleRoleRepository.saveAll(rolesToUpdate);
        articleRoleRepository.deleteArticleRolesIn(rolesToDelete);
    }

    public void updateRolesOld(List<ArticleRoleDto> requestedRoles, ArticleEntity article){
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
                        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Not Found Role"));
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
        if (memberUUID == null){
            throw new ApiException(ErrorCode.BAD_REQUEST, "You need to login in first.");
        }

        MemberResponse memberResponse = memberClient.getMemberInfo(memberUUID).getData();

        List<String> roles = memberResponse.getRoles().stream()
                .map(MemberRoleResponse::getName)
                .toList();
        List<RoleEntity> memberRoles = roleRepository.getRoles(roles);

        List<String> stacks = memberResponse.getStacks().stream()
                .map(Objects::toString)
                .toList();
        List<TechStackEntity> memberStacks = techStackRepository.getTechStacks(stacks);

        List<ArticleResponseDto> suggestions = articleRepository.getSuggestions(memberRoles, memberStacks);

        return suggestions.stream()
                .sorted(Comparator.comparingInt(suggestion ->
                        calculatePriority(suggestion, memberRoles, memberStacks)))
                .toList();
    }

    public List<ArticleResponseDto> getPopularPosts(){
        return articleRepository.getPopularPosts().stream()
                .toList();
    }

    int calculatePriority(
            ArticleResponseDto suggestion,
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
        if (memberUUID == null){
            throw new ApiException(ErrorCode.BAD_REQUEST, "You need to login in first.");
        }

        ArticleEntity article = articleRepository.findByArticleUUID(articleUUID)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Not Found Post"));

        if (!article.getMemberUUID().equals(memberUUID)){
            throw new ApiException(ErrorCode.BAD_REQUEST, "Only the author can delete the post.");
        }

        article.changeStatus((byte)0);
        return "Successfully deleted";
    }

    /**
     *  comment
     */
    public CommentResponseDto writeComment(CommentRequestDto request, UUID articleUUID, UUID memberUUID) {

        ArticleEntity article = articleRepository.findByArticleUUID(articleUUID)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Not Found Article"));

        CommentEntity commentEntity = CommentConverter.convertToEntity(request, article, memberUUID);

        return CommentConverter.convertToDto(commentRepository.save(commentEntity));
    }

    public CommentResponseDto updateComment(CommentRequestDto commentDto, UUID memberUUID) {

        CommentEntity commentEntity = commentRepository.findByCommentUUID(commentDto.getCommentUUID())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Not Found Comment"));

        if(commentEntity.getMemberUUID().equals(memberUUID)){
            // update
            commentEntity.changeContent(commentDto.getContent());
        }else {
            throw new ApiException(ErrorCode.FORBIDDEN_ERROR, "Only commenter can update the Comment.");
        }

        return CommentConverter.convertToDto(commentEntity);
    }

    public void deleteComment(UUID commentUUID, UUID memberUUID) {
        CommentEntity commentEntity = commentRepository.findByCommentUUID(commentUUID)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Not Found Comment"));

        if(commentEntity.getMemberUUID().equals(memberUUID)){
            commentRepository.delete(commentEntity);
        }else {
            throw new ApiException(ErrorCode.FORBIDDEN_ERROR, "Only commenter can delete the Comment.");
        }
    }


    public BookmarkResponse updateBookmark(UUID articleUUID, UUID memberUUID) {
        ArticleEntity article = articleRepository.findByArticleUUID(articleUUID)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "No Post Found"));

        Optional<BookmarkEntity> bookmark = bookmarkRepository.findByArticleIdAndMemberUUID(article.getId(), memberUUID);

        if(bookmark.isPresent()) {
            bookmarkRepository.delete(bookmark.get());
            article.deleteBookmark();
            return null;
        }

        BookmarkEntity newBookmark = BookmarkEntity.builder()
                .article(article)
                .memberUUID(memberUUID)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        bookmarkRepository.save(newBookmark);
        article.setBookmark();

        return BookmarkResponse.builder()
                .title(article.getTitle())
                .articleUUID(article.getArticleUUID())
                .plannedStartAt(article.getPlannedStartAt())
                .expiredAt(article.getExpiredAt())
                .build();
    }

    public List<BookmarkResponse> getBookmarks(UUID memberUUID) {
        List<BookmarkEntity> bookmarkList = bookmarkRepository.findAllByMemberUUID(memberUUID);
        List<BookmarkResponse> response = new ArrayList<>();

        for (BookmarkEntity bookmarkEntity : bookmarkList) {
            ArticleEntity article = articleRepository.findById(bookmarkEntity.getArticle().getId()).orElseThrow();
            response.add(BookmarkResponse.builder()
                    .articleUUID(article.getArticleUUID())
                    .title(article.getTitle())
                    .plannedStartAt(article.getPlannedStartAt())
                    .expiredAt(article.getExpiredAt())
                    .build());
        }

        return response;
    }

    public ChatClientResponseDto sendChatClient(UUID articleUUID) {
        ArticleEntity article = articleRepository.findByArticleUUID(articleUUID)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "No Article found"));

        return ChatClientResponseDto.builder()
                .roomName(article.getTitle())
                .writerUUID(article.getMemberUUID())
                .articleUUID(articleUUID)
                .build();
    }

    /**
     * UTILS
     */

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
