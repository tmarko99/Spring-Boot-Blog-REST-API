package com.springboot.blog.service;


import com.springboot.blog.entity.Tag;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.security.UserPrincipal;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, UserPrincipal userPrincipal);
    PostResponse getAllPosts(int pageNumber, int pageSize, String sortField, String sortDir);
    PostResponse getPostsByCategory(Long categoryId, int pageNumber, int pageSize, String sortField, String sortDir);
    PostDto getPostById(Long id);
    PostResponse findPostsByTag(Long id, int pageNumber, int pageSize, String sortField, String sortDir);
    PostDto updatePost(PostDto postDto, Long id, UserPrincipal userPrincipal);
    void deletePost(Long id);
    List<PostDto> getPostsCreatedByUser(Long id);
}
