package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Tag;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.TagRepository;
import com.springboot.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag getTagById(Long id) {
        return tagRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Tag", "id", id));
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag updateTag(Long id, Tag newTag) {
        Tag tag = tagRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Tag", "id", id));

        tag.setName(newTag.getName());

        tagRepository.save(tag);

        return tag;
    }
}
