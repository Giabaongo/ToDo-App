package com.giabaongo.ToDo_App.controller;

import com.giabaongo.ToDo_App.dto.request.UserCreationRequest;
import com.giabaongo.ToDo_App.dto.request.UserUpdateRequest;
import com.giabaongo.ToDo_App.entity.User;
import com.giabaongo.ToDo_App.repository.UserRepository;
import com.giabaongo.ToDo_App.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping()
    User createUser(@RequestBody UserCreationRequest request){
        return userService.createUser(request);
    }

    @GetMapping()
    List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") String userId){
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    User updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable("userId") String userId){
        userService.deleteUser(userId);
        return "User have been deleted";
    }
}
