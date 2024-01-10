package com.coconet.memberservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tech_stack")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TechStackEntity extends BaseEntity{
    // New : Category enum
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tech_stack_id")
    private Long id;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 10, nullable = false)
    private String category;
    @Column(length = 200, nullable = false)
    private String image;
}
