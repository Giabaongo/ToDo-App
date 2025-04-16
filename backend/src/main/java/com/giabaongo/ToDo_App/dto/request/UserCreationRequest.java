package com.giabaongo.ToDo_App.dto.request;

import lombok.Data;

@Data
public class UserCreationRequest {
    private String username;
    private String password;
}
