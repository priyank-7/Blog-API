package com.blog.Services.Impl;

import com.blog.Entities.Category;
import com.blog.Exceptions.ResourceNotFoundException;
import com.blog.Payloads.CategoryDto;
import com.blog.Repositories.CategoryRepository;
import com.blog.Services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        return this.modelMapper.map(this.categoryRepository.save(this.modelMapper.map(categoryDto, Category.class)),CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","Id",id));
        return this.modelMapper.map(this.categoryRepository.save(categoryDtoToCategory(categoryDto, category, id)),CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer id) {
        this.categoryRepository.delete(this.categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category","Id",id)));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return this.categoryRepository.findAll().stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        return this.modelMapper.map(this.categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("category","Id",id)),CategoryDto.class);
    }


    private Category categoryDtoToCategory(CategoryDto categoryDto, Category category, Integer id){
        category = this.modelMapper.map(categoryDto,Category.class);
        category.setCategoryId(id);
        return category;
    }
}
