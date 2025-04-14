package com.giabaongo.ToDo_App.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.giabaongo.ToDo_App.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, String> {
    List<Todo> findByUserUsername(String username);

    @Query("SELECT COUNT(t) FROM Todo t WHERE t.user.username = :username")
    long countByUserUsername(@Param("username") String username);

    @Query("SELECT t FROM Todo t WHERE t.user.username = :username " +
           "AND (:completed IS NULL OR t.completed = :completed) " +
           "AND (:startDate IS NULL OR t.createdAt >= :startDate) " +
           "AND (:endDate IS NULL OR t.createdAt <= :endDate) " +
           "AND (:searchText IS NULL OR " +
           "     (LOWER(t.title) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
           "     OR LOWER(t.description) LIKE LOWER(CONCAT('%', :searchText, '%'))))")
    List<Todo> findByUserUsernameAndFilters(
            @Param("username") String username,
            @Param("completed") Boolean completed,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("searchText") String searchText
    );
}
