package com.giabaongo.ToDo_App.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TodoResponse {
    private String id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
