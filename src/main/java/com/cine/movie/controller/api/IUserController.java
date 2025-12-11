package com.cine.movie.controller.api;

import com.cine.movie.domain.dto.users.UserCreateRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Tag(
        name = "2. Users",
        description = "Endpoints for user management."
)
@RequestMapping("/api/v1/users")
public interface IUserController {

    @Operation(
            summary = "Register new user",
            description = "⚠️ PUBLIC ENDPOINT - Does NOT require authentication. " +
                    "Creates a new user in the system with the provided data. " +
                    "The CPF (Brazilian tax identification number) and email must be unique. The password will be automatically encrypted."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data (validation failed)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "CPF or email already registered in the system",
                    content = @Content
            )
    })
    @PostMapping
    ResponseEntity<Void> registrar(
            @RequestBody
            @Valid
            UserCreateRequestDTO dto
    );
}
