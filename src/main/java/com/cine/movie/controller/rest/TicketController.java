package com.cine.movie.controller.rest;

import com.cine.movie.controller.api.ITicketController;
import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.cine.movie.domain.entity.TicketEntity;
import com.cine.movie.domain.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TicketController implements ITicketController {

    private final TicketService ticketService;

    @Override
    public ResponseEntity<TicketEntity> getAllTickets() {
        return null; // Implementar
    }

    @Override
    public ResponseEntity<TicketEntity> buyTicket(TicketCreateRequestDTO dto) {
        TicketEntity ticket = ticketService.buyTicket(dto);
        return ResponseEntity.status(201).body(ticket);
    }
}
