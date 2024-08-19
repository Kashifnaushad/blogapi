package com.myblogapp.service.impl;

import com.myblogapp.entity.Post;
import com.myblogapp.exception.ResourceNotFoundException;
import com.myblogapp.payload.PostDto;
import com.myblogapp.payload.PostResponse;
import com.myblogapp.repository.PostRepository;
import com.myblogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private ModelMapper mapper;
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {

        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post postEntity = postRepository.save(post);
        PostDto dto = mapToDto(postEntity);
        return dto;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPost = posts.getContent();
        List<PostDto> collect = listOfPost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(collect);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElement(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow((

        ) -> new ResourceNotFoundException("post", "id", id));

        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow((

        ) -> new ResourceNotFoundException("post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        postRepository.save(post);
       return mapToDto(post);

    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow((
        ) -> new ResourceNotFoundException("post", "id", id));
        postRepository.deleteById(id);

    }

    public PostDto mapToDto(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
        // PostDto postDto =new PostDto();
        // postDto.setId(post.getId());
        //postDto.setTitle(post.getTitle());
        //postDto.setDescription(post.getDescription());
        //postDto.setContent(post.getContent());
        return postDto;
    }

    public Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
}
