package com.springboot.blog.controller;

import com.springboot.blog.entity.User;
import com.springboot.blog.payload.UserProfile;
import com.springboot.blog.payload.UserSummary;
import com.springboot.blog.security.UserPrincipal;
import com.springboot.blog.service.PostService;
import com.springboot.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserSummary> getCurrentUser(@AuthenticationPrincipal UserPrincipal currentUser){
        return new ResponseEntity<>(userService.getCurrentUser(currentUser), HttpStatus.OK);
    }

    @GetMapping("/{username}/profile")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable("username") String username){
        return new ResponseEntity<>(userService.getUserProfile(username), HttpStatus.OK);
    }

    @PutMapping("/{username}/giveAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> giveAdmin(@PathVariable("username") String username){
        String result = userService.giveAdmin(username);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{username}/takeAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> takeAdmin(@PathVariable("username") String username){
        String result = userService.takeAdmin(username);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public ResponseEntity<User> updateUser(@PathVariable("username")String username, @RequestBody User user,
                                           @AuthenticationPrincipal UserPrincipal currentUser){
        return new ResponseEntity<>(userService.updateUser(username, user, currentUser), HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    public ResponseEntity<String> deleteUser(@PathVariable("username") String username,
                                             @AuthenticationPrincipal UserPrincipal currentUser){
        return new ResponseEntity<>(userService.deleteUser(username, currentUser), HttpStatus.OK);
    }

}
