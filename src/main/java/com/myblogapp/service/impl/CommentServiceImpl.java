package com.myblogapp.service.impl;

import com.myblogapp.entity.Comment;
import com.myblogapp.entity.Post;
import com.myblogapp.exception.BlogApiException;
import com.myblogapp.exception.ResourceNotFoundException;
import com.myblogapp.payload.CommentDto;
import com.myblogapp.repository.CommentRepository;
import com.myblogapp.repository.PostRepository;
import com.myblogapp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private ModelMapper mapper;
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow((

        ) -> new ResourceNotFoundException("post", "id", postId));

        //commentDto into commentEntity
        Comment comment = mapToComment(commentDto);

        //set post to comment entity
        comment.setPost(post);

        //save comment into DB
        Comment newComment = commentRepository.save(comment);

        //comment entity into dto
  return mapToDTO(newComment);


    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
       return comments.stream().map(comment->mapToDTO(comment)).collect(Collectors.toList());

    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", commentId)
        );

        if(comment.getPost().getId()!=post.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not belong to post");
        }

        return mapToDTO(comment);
    }


    @Override
    public CommentDto updateComment(long commentId, long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow((

        ) -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow((

        ) -> new ResourceNotFoundException("comment", "id", commentId));

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment = commentRepository.save(comment);

        return mapToDTO(updatedComment);


    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow((

        ) -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow((

        ) -> new ResourceNotFoundException("comment", "id", commentId));

        commentRepository.deleteById(commentId);


    }


    private Comment mapToComment(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }
    private CommentDto mapToDTO(Comment comment) {
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }
}
