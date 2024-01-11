package com.myblog7.service.serviceImpl;

import com.myblog7.entity.Post;
import com.myblog7.exception.ResourceNotFound;
import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;
import com.myblog7.repository.PostReposiory;
import com.myblog7.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    PostReposiory postReposiory;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostReposiory postReposiory,ModelMapper modelMapper) {
        this.postReposiory = postReposiory;
        this.modelMapper =modelMapper;
    }

    @Override
    public PostDto savePost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postReposiory.save(post);
        return mapToDto(savedPost);
    }

    @Override
    public void deletePost(long id) {
         postReposiory.deleteById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postReposiory.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post not found " + id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post savedPost = postReposiory.save(post);
        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postReposiory.findById(id).orElseThrow(
                ()-> new ResourceNotFound("Post not found with id "+id));
        return mapToDto(post);
    }

    @Override
    public PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pagePosts = postReposiory.findAll(pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> dtos = posts.stream().map(post ->
                mapToDto(post)).collect(Collectors.toList());
       // return dtos;
        PostResponse postResponse=new PostResponse();
        postResponse.setPostDto(dtos);
        postResponse.setPageNo(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setLast(pagePosts.isLast());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        return postResponse;
    }
    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
//        PostDto dto=new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
       return dto;

    }
   Post mapToEntity(PostDto postDto){
       Post post = modelMapper.map(postDto, Post.class);
//        Post post=new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
}
