package com.cine.movie.domain.dto.sessions;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

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
                type = "integer",
                minimum = "1"
        )
        @NotNull
        @Min(1)
        Long movieId,

        @Schema(
                description = "Price per seat for the session",
                example = "15.50",
                requiredMode = REQUIRED,
                type = "number",
                minimum = "0.01"
        )
        @NotNull
        @DecimalMin(value = "0.01")
        Double pricePerSeat,

        @Schema(
                description = "Start of the session",
                example = "2025-11-15T20:00:00",
                requiredMode = REQUIRED,
                type = "string",
                minLength = 19,
                maxLength = 19
        )
        //@Size(min = 19, max = 19)
        //@NotBlank
        LocalDateTime startSessionTime,

        @Schema(
                description = "Room ID where the session will take place",
                example = "5",
                requiredMode = REQUIRED,
                type = "integer",
                minimum = "1"
        )
        @NotNull
        @Min(1)
        Long roomId,

        @Schema(
                description = "Number of available seats for the session",
                example = "100",
                requiredMode = REQUIRED,
                type = "integer",
                minimum = "1"
        )
        @NotNull
        @Min(1)
        Integer availableSeats
) {
}