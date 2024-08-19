package com.myblogapp.service;

import com.myblogapp.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(long postId, long commentId);



    CommentDto updateComment(long commentId, long postId, CommentDto commentDto);

    void deleteComment(long postId, long commentId);
}
