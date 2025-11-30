package com.cine.movie.domain.dto.tickets;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(
    name = "TicketCreateRequestDTO",
    description = "DTO for creating a new ticket"
)
public record TicketCreateRequestDTO(

        @Schema(
                description = "ID of the session for which the ticket is being created",
                example = "2",
                requiredMode = REQUIRED,
                type = "long",
                minimum = "1"
        )
        @NotNull
        Long sessionId,

        @Schema(
                description = "ID of the user purchasing the ticket",
                example = "456",
                requiredMode = REQUIRED,
                type = "long",
                minimum = "1"
        )
        @NotNull
        Long userId,

        @Schema(
                description = "Seat code for the ticket",
                example = "A10",
                requiredMode = REQUIRED,
                type = "string",
                minLength = 1,
                maxLength = 5
        )
        @NotBlank
        String seatCode

) {
}