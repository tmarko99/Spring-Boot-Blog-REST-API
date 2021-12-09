package com.springboot.blog.security;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrUsername(usernameOrEmail, usernameOrEmail);

        if(user == null)
            throw new UsernameNotFoundException("User not found with this username or email: " + usernameOrEmail);

        return new UserPrincipal(user);
    }

//    private Collection<? extends GrantedAuthority> authorities(Set<Role> roles){
////        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
////
////        roles.forEach(role -> authorityList.add(new SimpleGrantedAuthority(role.getName())));
//
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }
}
