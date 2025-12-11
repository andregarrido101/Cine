package com.cine.movie.domain.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "LoginResponse",
        description = "Response from the authentication endpoint containing the JWT token and user data."
)
public record LoginResponseDTO(
        @Schema(description = "JWT token for authentication in requests.", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token,

        @Schema(description = "Authentication token type", example = "Bearer")
        String type,

        @Schema(description = "Authenticated user ID", example = "1")
        Long userId,

        @Schema(description = "Authenticated Username", example = "João Silva")
        String name,

        @Schema(description = "Authenticated user's email address", example = "joao.silva@email.com")
        String email
) {
    public LoginResponseDTO(String token, Long userId, String name, String email) {
        this(token, "Bearer", userId, name, email);
    }
}

