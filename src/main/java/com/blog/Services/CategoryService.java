package com.blog.Services;

import com.blog.Payloads.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    //Create
    CategoryDto createCategory(CategoryDto categoryDto);

    // Update
    CategoryDto updateCategory(CategoryDto categoryDto, Integer id);

    // Delete
    void deleteCategory(Integer id);

    // Get All
    List<CategoryDto> getAllCategories();

    // Get
    CategoryDto getCategoryById(Integer id);
}
