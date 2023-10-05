package com.coconet.memberservice.entity;

import com.coconet.memberservice.security.oauth.model.AuthProvider;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

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

    @Column(length = 255, nullable = false)
    private String providerId;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @OneToMany(mappedBy = "member")
    private List<MemberStackEntity> memberStacks;

    public void changeName(String name){
        this.name = name;
    }

    public void changeCareer(String career) {
        this.career = career;
    }
}
