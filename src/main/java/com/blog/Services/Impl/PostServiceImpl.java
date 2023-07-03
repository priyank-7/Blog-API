package com.blog.Services.Impl;

import com.blog.Entities.Category;
import com.blog.Entities.Post;
import com.blog.Entities.User;
import com.blog.Exceptions.ResourceNotFoundException;
import com.blog.Payloads.CategoryDto;
import com.blog.Payloads.PostDto;
import com.blog.Payloads.PostResponse;
import com.blog.Payloads.UserDto;
import com.blog.Repositories.CategoryRepository;
import com.blog.Repositories.PostRepository;
import com.blog.Repositories.UserRepository;
import com.blog.Services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        if (postDto.getImageUrl() == null){
            postDto.setImageUrl("default.jpg");
        }
        postDto.setCreatedOn(new Date());
        return postToPostDto(this.postRepository.save(postDtoToPost(postDto,userId,categoryId)));
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));
        return postToPostDto(this.postRepository.save(postDtoToPost(postDto,post)));
    }

    @Override
    public void deletePost(Integer postId) {
        this.postRepository.delete(this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId)));
    }

    @Override
    public PostDto getPost(Integer postId) {
        return postToPostDto(this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId)));
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pagesize, String sortBy, String sortOrder){

        Sort sort = (sortOrder.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pagesize, sort);
        Page<Post> page = this.postRepository.findAll(pageable);
        List<PostDto> content = page.getContent().stream().map((post) -> postToPostDto(post)).collect(Collectors.toList());
        return new PostResponse(content,page.getNumber(),page.getSize(),page.getTotalPages(),page.getTotalElements(),page.isLast());
    }

    @Override
    public List<PostDto> getAllPostsByCategory(Integer categoryId) {
        return this.postRepository.findByCategory((Category)this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId))).stream().map((post) -> postToPostDto(post)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAllPostsByUser(Integer userId) {
        return this.postRepository.findByUser((User) this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId))).stream().map((post) -> postToPostDto(post)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        return this.postRepository.findByTitleContaining(keyword).stream().map((post) -> postToPostDto(post)).collect(Collectors.toList());
    }




    private Post postDtoToPost(PostDto postdto, Integer userId, Integer categoryId){
        Post post = this.modelMapper.map(postdto,Post.class);
        post.setUser(this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",postdto.getUserDto().getUserId())));
        post.setCategory(this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Id",postdto.getCategoryDto().getCategoryId())));
        return post;
    }
    private PostDto postToPostDto(Post post){
        PostDto postDto = this.modelMapper.map(post,PostDto.class);
        postDto.setUserDto(this.modelMapper.map(post.getUser(), UserDto.class));
        postDto.setCategoryDto(this.modelMapper.map(post.getCategory(), CategoryDto.class));
        return postDto;
    }

    private Post postDtoToPost(PostDto postDto, Post post){
    if (!postDto.getTitle().isBlank()) {
        post.setTitle(postDto.getTitle());
    }
    if (!postDto.getContent().isBlank()){
        post.setContent(postDto.getContent());
    }
    if (!postDto.getImageUrl().isBlank()){
        post.setImageUrl(postDto.getImageUrl());
    }
    return post;
    }
}
