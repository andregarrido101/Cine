package com.cine.movie.controller.api;

import com.cine.movie.domain.dto.sessions.SessionCreateRequestDTO;
import com.cine.movie.domain.entity.SessionEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/sessions")
@Tag(name = "Session Management", description = "Endpoints for managing sessions")
public interface ISessionController {

    @Operation(summary = "Get all sessions", description = "Retrieves a list of all movie sessions available.")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully retrieved list of sessions",
                        content = @Content
                )
            }
    )
    ResponseEntity<List<SessionEntity>> getAllSessions();

    @Operation(summary = "Create a new session", description = "Creates a new session for a movie in a specific room at a given time.")
    @ApiResponses(
            value = {
                @ApiResponse(
                    responseCode = "201",
                    description = "Session created successfully",
                    content = @Content
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "No sessions found",
                        content = @Content
                )
            }
    )
    @PostMapping
    ResponseEntity<Void> createSession(
            @RequestBody
            @Valid
            SessionCreateRequestDTO dto
    );
}
