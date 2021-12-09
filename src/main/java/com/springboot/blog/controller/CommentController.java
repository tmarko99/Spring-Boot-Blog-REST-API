package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> allCommentsOnPost(@PathVariable("postId") Long id){
        return commentService.getAllComments(id);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") Long postId,
                                                     @PathVariable("commentId") Long commentId){
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto, @PathVariable("postId") long id){
        return new ResponseEntity<>(commentService.createComment(commentDto, id), HttpStatus.CREATED);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") Long postId,
                                                    @PathVariable("commentId") Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") Long postId,
                                                @PathVariable("commentId") Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment successfully deleted", HttpStatus.OK);
    }
}
