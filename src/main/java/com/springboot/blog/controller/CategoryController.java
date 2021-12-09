package com.springboot.blog.controller;

import com.springboot.blog.entity.Category;
import com.springboot.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id){
        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id,
                                                   @RequestBody Category category){
        return new ResponseEntity<>(categoryService.updateCategory(id, category), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }

}
