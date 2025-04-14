package com.giabaongo.ToDo_App.service;

import com.giabaongo.ToDo_App.dto.request.UserCreationRequest;
import com.giabaongo.ToDo_App.dto.request.UserUpdateRequest;
import com.giabaongo.ToDo_App.entity.User;
import com.giabaongo.ToDo_App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(UserCreationRequest request){
        User user = new User();

        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("Username already exists");
        }

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public User updateUser(String userId, UserUpdateRequest request){
        User user = getUser(userId);

        user.setPassword(request.getPassword());
        return userRepository.save(user);
    }
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
