package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.Tag;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
//    @Query("SELECT p FROM Post p WHERE p.category.id = ?1")
    Page<Post> findByCategory_Id(Long categoryId, Pageable pageable);
    Page<Post> findByTagsIn(List<Tag> tags, Pageable pageable);
    @Query("SELECT p FROM Post p WHERE p.user.id = ?1")
    List<Post> findByUser(Long id);
    @Query("SELECT COUNT(p.id) FROM Post p WHERE p.user.id = ?1")
    Long countByUser(Long userId);
}
