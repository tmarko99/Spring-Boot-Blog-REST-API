package com.springboot.blog.controller;

import com.springboot.blog.entity.Todo;
import com.springboot.blog.security.UserPrincipal;
import com.springboot.blog.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<Todo> getAllTodos(@AuthenticationPrincipal UserPrincipal currentUser){
        return todoService.getAllTodos(currentUser);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Todo> getTodo(@PathVariable("id") Long id,
                                        @AuthenticationPrincipal UserPrincipal currentUser){
        return new ResponseEntity<>(todoService.getTodo(id, currentUser), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Todo> addTodo(@Valid @RequestBody Todo todo,
                                        @AuthenticationPrincipal UserPrincipal currentUser){
        return new ResponseEntity<>(todoService.addTodo(todo, currentUser), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") Long id,
                                           @Valid @RequestBody Todo todo,
                                        @AuthenticationPrincipal UserPrincipal currentUser){
        return new ResponseEntity<>(todoService.updateTodo(id,todo, currentUser), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long id,
                                           @AuthenticationPrincipal UserPrincipal currentUser){
        todoService.deleteTodo(id, currentUser);
        return new ResponseEntity<>("Todo deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}/complete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> completeTodo(@PathVariable("id") Long id,
                                           @AuthenticationPrincipal UserPrincipal currentUser){
        return new ResponseEntity<>(todoService.completeTodo(id, currentUser), HttpStatus.OK);
    }

    @PutMapping("/{id}/unComplete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> unCompleteTodo(@PathVariable("id") Long id,
                                               @AuthenticationPrincipal UserPrincipal currentUser){
        return new ResponseEntity<>(todoService.unCompleteTodo(id, currentUser), HttpStatus.OK);
    }
}
