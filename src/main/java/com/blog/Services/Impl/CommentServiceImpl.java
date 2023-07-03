package com.blog.Services.Impl;

import com.blog.Entities.Comment;
import com.blog.Entities.Post;
import com.blog.Entities.User;
import com.blog.Exceptions.ResourceNotFoundException;
import com.blog.Payloads.CommentDto;
import com.blog.Repositories.CommentRepository;
import com.blog.Repositories.PostRepository;
import com.blog.Repositories.UserRepository;
import com.blog.Services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        return commentToCommentDto(this.commentRepository.save(commentDtoToComment(commentDto,postId,userId)));
    }

    @Override
    public void deleteComment(Integer commentId) {
        this.commentRepository.delete(this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","Id",commentId)));
    }

    private Comment commentDtoToComment (CommentDto commentDto, Integer postId, Integer userId){
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        Comment comment = this.modelMapper.map(commentDto,Comment.class);
        comment.setUser(user);
        comment.setPost(post);
        return comment;
    }

    private CommentDto commentToCommentDto(Comment comment){
        return this.modelMapper.map(comment,CommentDto.class);
    }
}
