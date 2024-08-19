package com.myblogapp.controller;

import com.myblogapp.payload.CommentDto;
import com.myblogapp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto>createComment(@PathVariable("postId")long postId, @RequestBody CommentDto commentDto){

    return new ResponseEntity<>(commentService.createComment(postId, commentDto),HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto>getCommentsByPostId(@PathVariable("postId")long postId){
        List<CommentDto> dto = commentService.getCommentsByPostId(postId);
        return dto;
    }
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable( "postId") long postId,
            @PathVariable("id") long commentId)
    {
        CommentDto dto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto>updateComment(@PathVariable("id")long commentId,
                                                   @PathVariable("postId")long postId,
                                                    @RequestBody CommentDto commentDto){
       return new ResponseEntity<>(commentService.updateComment(commentId,postId,commentDto),HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId")long postId,
                                                @PathVariable("id")long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("delete done",HttpStatus.OK);
    }
}
