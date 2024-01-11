package com.myblog7.service;

import com.myblog7.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto commentDto);
    String deleteComment(long postId,long commentId);

    List<CommentDto> getComments(long postId);

    Object getCommentById(long id, long postId);
}
