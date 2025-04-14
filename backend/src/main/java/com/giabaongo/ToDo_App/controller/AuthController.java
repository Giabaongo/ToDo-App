package com.giabaongo.ToDo_App.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.giabaongo.ToDo_App.dto.request.LoginRequest;
import com.giabaongo.ToDo_App.dto.response.LoginResponse;
import com.giabaongo.ToDo_App.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "Authentication", description = "Authentication management APIs")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @Operation(
        summary = "Login user",
        description = "Authenticate user and return JWT token"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully authenticated",
            content = @Content(schema = @Schema(implementation = LoginResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Invalid credentials",
            content = @Content
        )
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during login");
        }
    }

    //@PostMapping("/introspect")

}
