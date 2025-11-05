package com.cine.movie.domain.dto.sessions;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(
        name = "SessionCreateRequest",
        description = "Request DTO for creating a new session"
)
public record SessionCreateRequestDTO(

        @Schema(
                description = "ID of the movie for the session",
                example = "1",
                requiredMode = REQUIRED,
                type = "number",
                minLength = 1,
                maxLength = 100
        )
        @NotBlank
        @Size(min = 1, max = 100)
        Long movieId,

        @Schema(
                description = "Price per seat for the session",
                example = "15.50",
                requiredMode = REQUIRED,
                type = "number",
                minimum = "0.01"
        )
        @NotBlank
        @Size(min = 1)
        Double pricePerSeat,

        @Schema(
                description = "Time of the session",
                example = "2025-11-15T20:00:00",
                requiredMode = REQUIRED,
                type = "string",
                minLength = 19,
                maxLength = 19
        )
        @Size(min = 19, max = 19)
        @NotBlank
        String sessionTime,

        @Schema(
                description = "Room ID where the session will take place",
                example = "5",
                requiredMode = REQUIRED,
                type = "integer",
                minimum = "1"
        )
        @Size(min = 1)
        @NotBlank
        Long roomId,

        @Schema(
                description = "Number of available seats for the session",
                example = "100",
                requiredMode = REQUIRED,
                type = "integer",
                minimum = "1"
        )
        @Size(min = 1)
        @NotBlank
        Integer availableSeats
) {
}