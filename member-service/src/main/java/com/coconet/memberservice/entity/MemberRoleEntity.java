package com.coconet.memberservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "member_role")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRoleEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

}
