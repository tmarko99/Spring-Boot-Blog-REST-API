package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.security.UserPrincipal;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                    @RequestParam(value = "sortField", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortField,
                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        return postService.getAllPosts(pageNumber, pageSize, sortField, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/tag/{id}")
    public PostResponse getPostsByTag(@PathVariable("id") Long id,
                                      @RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
                                      @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                      @RequestParam(value = "sortField", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortField,
                                      @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        return postService.findPostsByTag(id, pageNumber, pageSize, sortField, sortDir);
    }

    @GetMapping("/category/{id}")
    public PostResponse getPostsByCategory(@PathVariable("id") Long id,
                                                           @RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                           @RequestParam(value = "sortField", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortField,
                                                           @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        return postService.getPostsByCategory(id, pageNumber, pageSize, sortField, sortDir);
    }

    @GetMapping("/byUser/{id}")
    public List<PostDto> getPostCreatedByUser(@PathVariable("id")Long id){
        return postService.getPostsCreatedByUser(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping()
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,
                                              @AuthenticationPrincipal UserPrincipal currentUser){
        return new ResponseEntity<>(postService.createPost(postDto, currentUser), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("id") Long id,
                                              @AuthenticationPrincipal UserPrincipal currentUser){
        return new ResponseEntity<>(postService.updatePost(postDto, id, currentUser), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id){
        postService.deletePost(id);

        return new ResponseEntity<>("Post entity deleted successfully", HttpStatus.OK);
    }
}
