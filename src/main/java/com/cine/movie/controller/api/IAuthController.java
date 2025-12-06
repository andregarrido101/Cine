package com.cine.movie.controller.api;

import com.cine.movie.domain.dto.auth.LoginRequestDTO;
import com.cine.movie.domain.dto.auth.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(
        name = "1. Authentication",
        description = "Public endpoints to authentication. Don't require JWT token."
)
@RequestMapping("/api/v1/auth")
public interface IAuthController {

    @Operation(
            summary = "Log in to the system",
            description = "⚠️ PUBLIC ENDPOINT - Does NOT require authentication. " +
                    "Authenticates a user in the system using CPF (Brazilian individual taxpayer registration number) or email as login and password. " +
                    "Returns a JWT token that must be used in the Authorization header of protected requests."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful. Returns the JWT token and user data.",
                    content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data (validation failed).",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials (incorrect login or password).",
                    content = @Content
            )
    })
    @PostMapping("/login")
    ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO);
}