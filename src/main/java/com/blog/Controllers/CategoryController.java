package com.blog.Controllers;

import com.blog.Payloads.ApiResponse;
import com.blog.Payloads.CategoryDto;
import com.blog.Services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Create Category
    @PostMapping()
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(this.categoryService.createCategory(categoryDto));
    }
    // Update Category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer id){
        return ResponseEntity.ok(this.categoryService.updateCategory(categoryDto, id));
    }

    // Delete Category
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id){
        this.categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse(true,"Category Deleted Successfully"));
    }

    // Get All Categories
    @GetMapping
    public ResponseEntity<?> getAllCategories(){
        return ResponseEntity.ok(this.categoryService.getAllCategories());
    }

    // Get Category
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer id){
        return ResponseEntity.ok(this.categoryService.getCategoryById(id));
    }
}
