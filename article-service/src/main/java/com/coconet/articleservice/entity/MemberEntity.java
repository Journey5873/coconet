package com.coconet.articleservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.*;

@Entity
@Table(name = "member")
@Getter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class MemberEntity extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(name = "member_uuid")
    private UUID memberUUID;

    public MemberEntity(Long id, String email, String name){
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
