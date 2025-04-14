package com.giabaongo.ToDo_App.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.giabaongo.ToDo_App.dto.request.TodoFilterRequest;
import com.giabaongo.ToDo_App.dto.request.TodoRequest;
import com.giabaongo.ToDo_App.dto.response.TodoResponse;
import com.giabaongo.ToDo_App.entity.Todo;
import com.giabaongo.ToDo_App.entity.User;
import com.giabaongo.ToDo_App.repository.TodoRepository;
import com.giabaongo.ToDo_App.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public List<TodoResponse> getAllTodos(String username){
        log.info("Getting all todos for user: {}", username);
        long totalTodos = todoRepository.countByUserUsername(username);
        log.info("Total todos for user {}: {}", username, totalTodos);
        
        return todoRepository.findByUserUsername(username)
                .stream()
                .map(todo -> convertToResponse(todo))
                .collect(Collectors.toList());
    }

    public List<TodoResponse> getFilteredTodos(String username, TodoFilterRequest filterRequest) {
        log.info("Filtering todos for user: {}", username);
        long totalTodos = todoRepository.countByUserUsername(username);
        log.info("Total todos for user {}: {}", username, totalTodos);
        
        log.info("Filter criteria - completed: {}, searchText: {}, startDate: {}, endDate: {}", 
                filterRequest.getCompleted(), 
                filterRequest.getSearchText(),
                filterRequest.getStartDate(),
                filterRequest.getEndDate());

        List<Todo> todos = todoRepository.findByUserUsernameAndFilters(
                username,
                filterRequest.getCompleted(),
                filterRequest.getStartDate(),
                filterRequest.getEndDate(),
                filterRequest.getSearchText()
        );

        log.info("Found {} todos matching criteria", todos.size());
        
        return todos.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private TodoResponse convertToResponse(Todo todo) {
        TodoResponse response = new TodoResponse();
        response.setId(todo.getId());
        response.setTitle(todo.getTitle());
        response.setDescription(todo.getDescription());
        response.setCompleted(todo.isCompleted());
        response.setCreatedAt(todo.getCreatedAt());
        response.setUpdatedAt(todo.getUpdatedAt());
        return response;
    }

    @Transactional
    public TodoResponse createTodo(TodoRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        todo.setUser(user);

        Todo savedTodo = todoRepository.save(todo);
        return mapToResponse(savedTodo);
    }

    @Transactional
    public TodoResponse updateTodo(String id, TodoRequest request, String username) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!todo.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Not authorized to update this todo");
        }

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());

        Todo updatedTodo = todoRepository.save(todo);
        return mapToResponse(updatedTodo);
    }

    public void deleteTodo(String id, String username) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (!todo.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Not authorized to delete this todo");
        }

        todoRepository.delete(todo);
    }

    private TodoResponse mapToResponse(Todo todo) {
        TodoResponse response = new TodoResponse();
        response.setId(todo.getId());
        response.setTitle(todo.getTitle());
        response.setDescription(todo.getDescription());
        response.setCompleted(todo.isCompleted());
        response.setCreatedAt(todo.getCreatedAt());
        response.setUpdatedAt(todo.getUpdatedAt());
        return response;
    }
}
