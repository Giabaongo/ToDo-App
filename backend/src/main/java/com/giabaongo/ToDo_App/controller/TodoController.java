package com.giabaongo.ToDo_App.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giabaongo.ToDo_App.dto.request.TodoFilterRequest;
import com.giabaongo.ToDo_App.dto.request.TodoRequest;
import com.giabaongo.ToDo_App.dto.response.TodoResponse;
import com.giabaongo.ToDo_App.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/todos")
@Tag(name = "Todo", description = "Todo management APIs")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @Operation(
        summary = "Get all todos",
        description = "Retrieve all todos for the authenticated user"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved todos",
            content = @Content(schema = @Schema(implementation = TodoResponse.class))
        )
    })
    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(todoService.getAllTodos(username));
    }

    @Operation(
        summary = "Filter todos",
        description = "Filter todos based on completion status, date range, and search text"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully filtered todos",
            content = @Content(schema = @Schema(implementation = TodoResponse.class))
        )
    })
    @PostMapping("/filter")
    public ResponseEntity<List<TodoResponse>> filterTodos(
            @Valid @RequestBody TodoFilterRequest filterRequest,
            Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(todoService.getFilteredTodos(username, filterRequest));
    }

    @Operation(
        summary = "Create todo",
        description = "Create a new todo for the authenticated user"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully created todo",
            content = @Content(schema = @Schema(implementation = TodoResponse.class))
        )
    })
    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(
            @Valid @RequestBody TodoRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(todoService.createTodo(request, username));
    }

    @Operation(
        summary = "Update todo",
        description = "Update an existing todo for the authenticated user"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully updated todo",
            content = @Content(schema = @Schema(implementation = TodoResponse.class))
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @PathVariable String id,
            @Valid @RequestBody TodoRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(todoService.updateTodo(id, request, username));
    }

    @Operation(
        summary = "Delete todo",
        description = "Delete a todo for the authenticated user"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Successfully deleted todo"
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable String id,
            Authentication authentication) {
        String username = authentication.getName();
        todoService.deleteTodo(id, username);
        return ResponseEntity.noContent().build();
    }
}
