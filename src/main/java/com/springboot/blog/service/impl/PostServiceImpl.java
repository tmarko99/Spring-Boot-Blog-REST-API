package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.Tag;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.repository.TagRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.UserPrincipal;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", currentUser.getUser().getId()));

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        List<Tag> tags = new ArrayList<>(postDto.getTags().size());

        for (String name : postDto.getTags()) {
            Tag tag = tagRepository.findByName(name);
            tag = tag == null ? tagRepository.save(new Tag(name)) : tag;

            tags.add(tag);
        }



        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setTags(tags);
        post.setCategory(category);
        post.setUser(user);

        Post newPost = postRepository.save(post);

        return mapToDTO(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();

//        List<PostDto> allPosts = new ArrayList<>();
//        for (Post post : all){
//            PostDto postDto = mapToDTO(post);
//            allPosts.add(postDto);
//        }

        List<PostDto> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostResponse getPostsByCategory(Long categoryId, int pageNumber, int pageSize, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> posts = postRepository.findByCategory_Id(categoryId, pageable);

        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", id));

        PostDto postDto = mapToDTO(post);

        return postDto;

    }

    @Override
    public PostResponse findPostsByTag(Long id, int pageNumber, int pageSize, String sortField, String sortDir) {
        Tag tag = tagRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Tag", "id", id));

        Sort sort = Sort.by(sortField);
        sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> posts = postRepository.findByTagsIn(Collections.singletonList(tag), pageable);
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }


    @Override
    public PostDto updatePost(PostDto postDto, Long id, UserPrincipal currentUser) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", id));

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        if(post.getUser().getId().equals(currentUser.getId()) ||
                currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            post.setTitle(postDto.getTitle());
            post.setDescription(postDto.getDescription());
            post.setContent(postDto.getContent());
            post.setCategory(category);

        }
        Post updatedPost = postRepository.save(post);

        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<PostDto> getPostsCreatedByUser(Long id) {
        User userById = userRepository.getById(id);

        List<Post> postByUserIn = postRepository.findByUser(userById.getId());

        List<PostDto> collect = postByUserIn.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        return collect;
    }

    private PostDto mapToDTO(Post post){
        PostDto postDto = modelMapper.map(post, PostDto.class);

//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());

        return postDto;
    }

    private Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);

//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        return post;
    }
}
