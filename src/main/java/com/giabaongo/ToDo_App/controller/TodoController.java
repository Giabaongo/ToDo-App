package com.giabaongo.ToDo_App.controller;

import com.giabaongo.ToDo_App.dto.request.TodoRequest;
import com.giabaongo.ToDo_App.dto.response.TodoResponse;
import com.giabaongo.ToDo_App.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(todoService.getAllTodos(username));
    }

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(
            @Valid @RequestBody TodoRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(todoService.createTodo(request, username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @PathVariable String id,
            @Valid @RequestBody TodoRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(todoService.updateTodo(id, request, username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable String id,
            Authentication authentication) {
        String username = authentication.getName();
        todoService.deleteTodo(id, username);
        return ResponseEntity.noContent().build();
    }
}
