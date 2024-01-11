package com.myblog7.service.serviceImpl;

import com.myblog7.entity.Comment;
import com.myblog7.entity.Post;
import com.myblog7.exception.ResourceNotFound;
import com.myblog7.exception.ResourceNotFound1;
import com.myblog7.payload.CommentDto;

import com.myblog7.repository.CommentRepository;
import com.myblog7.repository.PostReposiory;
import com.myblog7.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;

    private ModelMapper modelMapper;
    private PostReposiory postReposiory;


    public CommentServiceImpl(CommentRepository commentRepository,
                              ModelMapper modelMapper,
                              PostReposiory postReposiory) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.postReposiory=postReposiory;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment=mapToEntity(commentDto);
        Post post = postReposiory.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id " + postId));
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);
        CommentDto dto=mapToDto(savedComment);
        return dto;
    }

    @Override
    public String deleteComment(long postId, long commentId) {
            Post post = postReposiory.findById(postId).orElseThrow(
                    () -> new ResourceNotFound("Post not found with id " + postId));
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId);
        if (comment == null)
            throw new ResourceNotFound("comment not found for postId "+postId+
                    " with commentId " + commentId);
            commentRepository.deleteById(commentId);
            return "Comment Deleted Successfully  ";
    }

    @Override
    public List<CommentDto> getComments(long postId) {

            Post post = postReposiory.findById(postId).orElseThrow(
                    ()-> new ResourceNotFound("Post not found with id "+postId));
            List<Comment> comments = commentRepository.findByPostId(postId);
            List<CommentDto> dtos = comments.stream().map(comment ->
                    mapToDto(comment)).collect(Collectors.toList());
            return dtos;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        postReposiory.findById(postId).orElseThrow(
                () -> new ResourceNotFound("post not found exception with postId "
                        + postId));

        Comment comment = commentRepository.findByPostIdAndId(postId, commentId);
        if (comment == null)
            throw new ResourceNotFound("comment not found with id " + commentId);
        CommentDto commentDto = mapToDto(comment);

        return commentDto;
    }



    private CommentDto mapToDto(Comment savedComment) {
        CommentDto dto = modelMapper.map(savedComment, CommentDto.class);
        return dto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }
}
