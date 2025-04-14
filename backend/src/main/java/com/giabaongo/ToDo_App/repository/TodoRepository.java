package com.giabaongo.ToDo_App.repository;

import com.giabaongo.ToDo_App.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, String> {
    List<Todo> findByUserUsername(String username);
}
