package com.cine.movie.domain.dto.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(
        name = "LoginRequest",
        description = "Data required for user authentication in the system."
)
public record LoginRequestDTO(
        @Schema(
                description = "User login",
                example = "andregarrido@gmail.com",
                requiredMode = REQUIRED
        )
        @NotBlank(message = "Login is required")
        String login,

        @Schema(
                description = "User password",
                example = "securepassword123",
                requiredMode = REQUIRED
        )
        @NotBlank(message = "Password is required")
        String password
) {
}

