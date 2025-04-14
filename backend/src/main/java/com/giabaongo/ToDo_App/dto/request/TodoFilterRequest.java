package com.giabaongo.ToDo_App.dto.request;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TodoFilterRequest {
    private Boolean completed;
    private String searchText;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
} 