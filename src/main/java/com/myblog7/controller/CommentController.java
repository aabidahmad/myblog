package com.myblog7.controller;

import com.myblog7.payload.CommentDto;
import com.myblog7.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class CommentController {
    private CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService=commentService;
    }
    //url
    //http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<?>createComment(@PathVariable(value = "postId")long postId,
                                          @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto),HttpStatus.CREATED);
//                commentDto),HttpStatus.CREATED);
//        CommentDto createdComment = commentService.createComment(postId, commentDto);
//
//        if (createdComment != null) {
//            // Successfully created the comment
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
//        } else {
//            // Comment creation failed, handle the error
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
//            // Or you can return a custom error DTO, throw an exception, etc.
//        }
    }
    //url
    //http://localhost:8080/api/deleteComment/1/1
    @DeleteMapping("/deleteComment/{postId}/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long postId, @PathVariable long commentId) {
        System.out.print("edfsd");
        String result = commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok(result);
    }
    //url
    //http://localhost:8080/api/comments/1
    @GetMapping("comments/{postId}")
    public List<CommentDto> getComments(@PathVariable long postId){
        List<CommentDto> comments = commentService.getComments(postId);
        return comments;
    }
    //url
    //http://localhost:8080/api/comments/1/1
    @GetMapping("comments/{postId}/{commentId}")
    public ResponseEntity<?> getComments(@PathVariable long postId, @PathVariable long commentId) {
        Object commentById = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentById,HttpStatus.OK);
    }


}
