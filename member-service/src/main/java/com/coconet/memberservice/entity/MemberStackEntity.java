package com.coconet.memberservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_stack")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberStackEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_stack_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;
    @ManyToOne
    @JoinColumn(name = "tech_stack_id")
    private TechStackEntity techStack;

    public MemberStackEntity(MemberEntity member, TechStackEntity techStack) {
        this.member = member;
        this.techStack = techStack;
    }
}
