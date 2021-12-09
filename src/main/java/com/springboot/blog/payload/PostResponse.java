package com.springboot.blog.payload;

import com.springboot.blog.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class PostResponse {
    private List<PostDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
