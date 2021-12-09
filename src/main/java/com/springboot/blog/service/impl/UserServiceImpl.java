package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.payload.UserProfile;
import com.springboot.blog.payload.UserSummary;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.UserPrincipal;
import com.springboot.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserSummary getCurrentUser(UserPrincipal userPrincipal) {
        return new UserSummary(userPrincipal.getId(), userPrincipal.getUsername(), userPrincipal.getUser().getName());
    }

    @Override
    public UserProfile getUserProfile(String username) {
        User user = userRepository.findByUsername(username);

        Long postCount = postRepository.countByUser(user.getId());

        return new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getEmail(), postCount);


    }

    @Override
    public String giveAdmin(String username) {
        User user = userRepository.findByUsername(username);
        Set<Role> currentUserRoles = user.getRoles();
        List<String> roleNames = currentUserRoles.stream().map(role -> role.getName()).collect(Collectors.toList());

        Set<Role> roles = new HashSet<>(currentUserRoles);

        if(roleNames.contains("ROLE_ADMIN")){
            return "User already have admin role";
        }
        else{
            roles.add(roleRepository.findByName("ROLE_ADMIN"));
            user.setRoles(roles);
            userRepository.save(user);
        }

        return "Admin role is successfully given to user";
    }

    @Override
    public String takeAdmin(String username) {
        User user = userRepository.findByUsername(username);
        Set<Role> currentUserRoles = user.getRoles();
        List<String> roleNames = currentUserRoles.stream().map(role -> role.getName()).collect(Collectors.toList());

        Set<Role> roles = new HashSet<>(currentUserRoles);

        if(!roleNames.contains("ROLE_ADMIN")){
            return "User don't have given admin role";
        }
        else{
            roles.removeIf(role -> role.getName().equals("ROLE_ADMIN"));
            user.setRoles(roles);
            userRepository.save(user);
        }

        return "Admin role is successfully taken from user";
    }

    @Override
    public User updateUser(String username, User newUser, UserPrincipal userPrincipal) {
        User user = userRepository.findByUsername(username);
        User updatedUser = null;

        if(user.getId().equals(userPrincipal.getId()) ||
        userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            user.setName(newUser.getName());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));

            updatedUser = userRepository.save(user);
        }

        return updatedUser;
    }

    @Override
    public String deleteUser(String username, UserPrincipal currentUser) {
        User user = userRepository.findByUsername(username);

        if(!user.getId().equals(currentUser.getId()) ||
                currentUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,
                    "You don't have permission to delete profile of: " + username);
        }

        userRepository.deleteById(user.getId());

        return  "You successfully deleted profile of: " + username;
    }
}
