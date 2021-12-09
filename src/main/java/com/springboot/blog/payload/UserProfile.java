package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private String email;
    private Long postCount;
}
