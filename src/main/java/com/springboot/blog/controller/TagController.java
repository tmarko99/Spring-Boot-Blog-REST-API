package com.springboot.blog.controller;

import com.springboot.blog.entity.Tag;
import com.springboot.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public List<Tag> getAllTags(){
        return tagService.getAllTags();
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable("id")Long id){
        return tagService.getTagById(id);
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag){
        return new ResponseEntity<>(tagService.createTag(tag), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable("id")Long id, @RequestBody Tag tag){
        return new ResponseEntity<>(tagService.updateTag(id, tag), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable("id")Long id){
        tagService.deleteTag(id);

        return new ResponseEntity<>("Tag deleted successfully", HttpStatus.OK);
    }
}
