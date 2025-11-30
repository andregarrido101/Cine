package com.cine.movie.controller.api;

import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.cine.movie.domain.dto.tickets.TicketResponseDTO;
import com.cine.movie.domain.entity.TicketEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/tickets")
@Tag(name = "Ticket Management", description = "Endpoints for managing tickets")
public interface ITicketController {

    @Operation(summary = "Get all tickets", description = "Retrieves a list of all tickets available.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved list of tickets",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No tickets found",
                            content = @Content
                    )
            }
    )
    @GetMapping
    ResponseEntity<List<TicketEntity>> getAllTickets();

    @Operation(summary = "Create a new ticket", description = "Creates a new ticket for a specific session and seat.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Ticket created successfully",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Session or seat not found",
                            content = @Content
                    )
            }
    )
    @PostMapping
    ResponseEntity<TicketResponseDTO> buyTicket(
            @RequestBody
            @Valid
            TicketCreateRequestDTO dto
    );
}