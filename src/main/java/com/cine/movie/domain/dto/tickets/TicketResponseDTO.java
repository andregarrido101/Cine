package com.cine.movie.domain.dto.tickets;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(
        name = "TicketResponseDTO",
        description = "DTO send to user"
)
public record TicketResponseDTO(

        @Schema(
                description = "Username for which the ticket was purchased",
                example = "João",
                requiredMode = REQUIRED,
                type = "string",
                minimum = "1"
        )
        String username,

        @Schema(
                description = "Session time for which the ticket was purchased",
                example = "2025-11-15T20:00:00",
                requiredMode = REQUIRED,
                type = "string",
                minimum = "1"
        )
        LocalDateTime startSessionTime,

        @Schema(
                description = "Room number for which the ticket was purchased",
                example = "101",
                requiredMode = REQUIRED,
                type = "long",
                minimum = "1"
        )
        Integer roomNumber,

        @Schema(
                description = "Seat code for which the ticket was purchased",
                example = "A01",
                requiredMode = REQUIRED,
                type = "string",
                minimum = "1"
        )
        String seatCode
) {
}