package com.springboot.blog.service;

import com.springboot.blog.entity.User;
import com.springboot.blog.payload.UserProfile;
import com.springboot.blog.payload.UserSummary;
import com.springboot.blog.security.UserPrincipal;

public interface UserService {
    UserSummary getCurrentUser(UserPrincipal userPrincipal);
    UserProfile getUserProfile(String username);
    String giveAdmin(String username);
    String takeAdmin(String username);
    User updateUser(String username, User newUser, UserPrincipal userPrincipal);
    String deleteUser(String username, UserPrincipal currentUser);
}
