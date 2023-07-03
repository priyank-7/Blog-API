package com.blog.Services;

import com.blog.Payloads.PostDto;
import com.blog.Payloads.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    PostDto getPost(Integer postId);

    PostResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

    List<PostDto> getAllPostsByCategory(Integer categoryId);

    List<PostDto> getAllPostsByUser(Integer userId);

    List<PostDto> searchPost(String keyword);
}
