package com.springboot.blog.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "todos", uniqueConstraints = @UniqueConstraint(columnNames = {"title"}))
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    private Boolean completed;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
