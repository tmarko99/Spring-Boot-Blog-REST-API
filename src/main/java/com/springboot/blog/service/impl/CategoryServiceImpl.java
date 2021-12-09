package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();

    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", categoryId));
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Category oldCategory = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", categoryId));


        oldCategory.setName(category.getName());

        Category updatedCategory = categoryRepository.save(oldCategory);

        return updatedCategory;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
