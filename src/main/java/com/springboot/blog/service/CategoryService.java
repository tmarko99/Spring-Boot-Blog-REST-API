package com.springboot.blog.service;

import com.springboot.blog.entity.Category;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category createCategory(Category category);
    Category getCategoryById(Long categoryId);
    Category updateCategory(Long categoryId, Category category);
    void deleteCategory(Long categoryId);
}
