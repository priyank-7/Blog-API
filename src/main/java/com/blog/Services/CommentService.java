package com.blog.Services;


import com.blog.Entities.Comment;
import com.blog.Payloads.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);

    void deleteComment(Integer commentId);

}
