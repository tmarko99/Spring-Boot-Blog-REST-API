package com.springboot.blog.service;

import com.springboot.blog.entity.Tag;

import java.util.List;

public interface TagService {
    Tag createTag(Tag tag);
    List<Tag> getAllTags();
    Tag getTagById(Long id);
    void deleteTag(Long id);
    Tag updateTag(Long id, Tag tag);
}
