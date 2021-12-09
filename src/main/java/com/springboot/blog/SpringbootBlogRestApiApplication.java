package com.springboot.blog;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class SpringbootBlogRestApiApplication{

//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private RoleRepository roleRepository;
//
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		Role role = new Role();
//		role.setName("ROLE_ADMIN");
//		Role role1 = new Role();
//		role1.setName("ROLE_USER");
//		roleRepository.saveAll(Arrays.asList(role, role1));
//
//		User user = new User();
//		user.setEmail("user@email.com");
//		user.setUsername("user");
//		user.setPassword(passwordEncoder.encode("password"));
//		user.setName("User Novi");
//		user.setRoles(Collections.singleton(role1));
//
//		userRepository.save(user);
//
//		User user1 = new User();
//		user1.setEmail("admin@email.com");
//		user1.setUsername("admin");
//		user1.setPassword(passwordEncoder.encode("admin"));
//		user1.setName("Admin");
//		user1.setRoles(Collections.singleton(role));
//
//		userRepository.save(user1);
//
//
//	}
}
