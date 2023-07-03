package com.blog.Controllers;

import com.blog.Config.AppConstants;
import com.blog.Payloads.ApiResponse;
import com.blog.Payloads.PostDto;
import com.blog.Payloads.PostResponse;
import com.blog.Services.FileService;
import com.blog.Services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/{categoryId}/post")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
        return ResponseEntity.ok(this.postService.createPost(postDto, userId, categoryId));
    }

    // Get By User
    @GetMapping("/user/{userId}/post")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
        return ResponseEntity.ok(this.postService.getAllPostsByUser(userId));
    }

    @GetMapping("/category/{categoryId}/post")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
        return ResponseEntity.ok(this.postService.getAllPostsByCategory(categoryId));
    }

    @GetMapping("/post")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = AppConstants.DEFAULT_SORT_ORDER, required = false) String sortOrder){
        return ResponseEntity.ok(this.postService.getAllPosts(pageNumber,pageSize,sortBy, sortOrder));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Integer postId){
        return ResponseEntity.ok(this.postService.getPost(postId));
    }

    @DeleteMapping("/user/{userId}/post/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ApiResponse(true,"Post deleted successfully");
    }

    @PutMapping("/user/{userId}/post/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
        return ResponseEntity.ok(this.postService.updatePost(postDto,postId));
    }

    @GetMapping("/post/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable(value = "keyword") String keyword){
        return ResponseEntity.ok(this.postService.searchPost(keyword));
    }

    @PostMapping("/user/{userId}/post/{postId}/image")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId
    ) throws IOException {
        PostDto postDto = this.postService.getPost(postId);
        postDto.setImageUrl(this.fileService.uploadImage(this.path, image));
        return ResponseEntity.ok(this.postService.updatePost(postDto,postId));
    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable(value = "imageName") String imageName,
            HttpServletResponse response
    ) throws IOException{
        InputStream resource = this.fileService.getResource(this.path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
