package com.springboot.blog.repository;

import com.springboot.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailOrUsername(String username, String email);
    User findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
