package com.coconet.memberservice.entity;

import com.coconet.memberservice.dto.UpdateStackRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tech_stack")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TechStackEntity extends BaseEntity{
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
