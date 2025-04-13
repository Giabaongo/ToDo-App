package com.giabaongo.ToDo_App.dto.request;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class TodoRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private boolean completed;
}
