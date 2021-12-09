package com.springboot.blog.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
