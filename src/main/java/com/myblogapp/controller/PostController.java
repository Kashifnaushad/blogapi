package com.myblogapp.controller;

import com.myblogapp.payload.PostDto;
import com.myblogapp.payload.PostResponse;
import com.myblogapp.service.PostService;
import com.myblogapp.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    //create blog post rest api
    @PostMapping
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto, BindingResult result) {
        // Object is super most class in java.
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //here by default status 200 will create automatically so, we don't need to use ResponseEntity.
    //http://localhost:8080/api/posts?pageNo=0&pageSize=10&sortBy=title&sortDir=desc
    @GetMapping
    public PostResponse getAllPost(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = true) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = true) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ){
    return postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
    }
    //http:localhost:8080/api/posts/id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto>getPostById(@PathVariable("id")long id){

        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto>updatePost(@RequestBody PostDto postDto, @PathVariable("id")long id){
        return new ResponseEntity<>(postService.updatePost(postDto , id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deletePost(@PathVariable("id")long id){
        postService.deletePost(id);
      return new ResponseEntity<>("delete done", HttpStatus.OK)  ;
    }
}
