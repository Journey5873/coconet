package com.coconet.memberservice.entity;

import com.coconet.memberservice.security.oauthModel.AuthProvider;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.*;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class MemberEntity extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String career;

    @Column(length = 200)
    private String profileImage;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(nullable = false)
    private Byte isActivated;

    @Column(columnDefinition = "BINARY(16)", name = "member_uuid",
            nullable = false, unique = true)
    private UUID memberUUID;

    @Column(length = 200)
    private String bio;

    @Column(length = 200)
    private String githubLink;
    @Column(length = 200)
    private String blogLink;
    @Column(length = 200)
    private String notionLink;

    @OneToMany(mappedBy = "member")
    private List<MemberStackEntity> memberStacks;

    @OneToMany(mappedBy = "member")
    private List<MemberRoleEntity> memberRoles;

    public void register(String name, String career, String github, String blog, String notion, String bio) {
        this.name = name;
        this.career = career;
        this.githubLink = github;
        this.blogLink = blog;
        this.notionLink = notion;
        this.bio = bio;
    }
    public void changeName(String name){
        this.name = name;
    }

    public void changeCareer(String career) {this.career = career;}

    public void changeProfileImage(String profileImage) { this.profileImage = profileImage;}

    public void changeBio(String bio){this.bio = bio;}

    public void changeGithubLink(String githubLink) { this.githubLink = githubLink;}

    public void changeBlogLink(String blogLink) { this.blogLink = blogLink;}

    public void changeNotionLink(String notionLink) { this.notionLink = notionLink;}

    public void deleteUser() { this.isActivated = 0;}
}
