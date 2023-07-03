package com.blog.Controllers;

import com.blog.Payloads.ApiResponse;
import com.blog.Payloads.CommentDto;
import com.blog.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/user/{userId}/post/{postId}/comment")
    public ResponseEntity<CommentDto> addComment(
            @RequestBody CommentDto comment,
            @PathVariable Integer userId,
            @PathVariable Integer postId
    ){
        return ResponseEntity.ok(this.commentService.createComment(comment,userId,postId));
    }

    @DeleteMapping("/user/{userId}/post/{postId}/comment/{commentId}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentId){
        return new ResponseEntity<>(new ApiResponse(true,"Comment Deleted Successfully"), HttpStatus.OK);
    }
}
