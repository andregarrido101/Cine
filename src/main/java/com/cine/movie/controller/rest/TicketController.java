package com.cine.movie.controller.rest;

import com.cine.movie.controller.api.ITicketController;
import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.cine.movie.domain.dto.tickets.TicketResponseDTO;
import com.cine.movie.domain.entity.TicketEntity;
import com.cine.movie.domain.mapper.TicketMapper;
import com.cine.movie.domain.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicketController implements ITicketController {

    private final TicketService ticketService;

    @Override
    public ResponseEntity<List<TicketEntity>> getAllTickets() {
        List<TicketEntity> tickets = ticketService.getAllTickets();
        return ResponseEntity.status(200).body(tickets);
    }

    @Override
    public ResponseEntity<TicketResponseDTO> buyTicket(TicketCreateRequestDTO dto) {
        TicketResponseDTO ticket = ticketService.buyTicket(dto);
        return ResponseEntity.status(201).body(ticket);
    }
}