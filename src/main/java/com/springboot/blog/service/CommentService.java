package com.springboot.blog.service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;

import java.util.List;
import java.util.Set;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, long id);
    List<CommentDto> getAllComments(long id);
    CommentDto getCommentById(Long postId, Long commentId);
    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);
    void deleteComment(Long postId, Long commentId);
}
