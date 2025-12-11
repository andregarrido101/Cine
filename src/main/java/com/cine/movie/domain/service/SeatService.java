package com.cine.movie.domain.service;

import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.cine.movie.domain.entity.SeatEntity;
import com.cine.movie.domain.entity.SessionEntity;
import com.cine.movie.domain.entity.TicketEntity;
import com.cine.movie.domain.mapper.SeatMapper;
import com.cine.movie.domain.repository.RoomRepository;
import com.cine.movie.domain.repository.SeatRepository;
import com.cine.movie.domain.repository.SessionRepository;
import com.cine.movie.domain.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeatService {

    private final SessionRepository sessionRepository;
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;

    private final SeatMapper mapper;

    public void registrySeatPurchased(TicketEntity ticketSaved, TicketCreateRequestDTO dto) {

        log.info("Registering seat purchased in the database");

        var session = verifySessionExistence(dto);
        var ticket = verifyTicketExistence(ticketSaved);

        var entity = mapper.ticketCreateRequestDTOConvertToEntity(dto);
        entity.setSession(session);
        entity.setTicket(ticket);

        seatRepository.save(entity);
    }

    public SessionEntity verifySessionExistence(TicketCreateRequestDTO dto) {
        return sessionRepository.findById(dto.sessionId())
                .orElseThrow(() -> new IllegalArgumentException("Session not found."));
    }

    public TicketEntity verifyTicketExistence(TicketEntity ticketSaved) {
        return ticketRepository.findById(ticketSaved.getId())
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found."));
    }
}
