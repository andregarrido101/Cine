package com.cine.movie.domain.service;

import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.cine.movie.domain.entity.SeatEntity;
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
    private final RoomRepository roomRepository;
    private final SeatMapper mapper;

    public void registrySeatPurchased(TicketCreateRequestDTO dto) {

        log.info("Registering seat purchased in the database");

        var session = sessionRepository.findById(dto.sessionId())
                .orElseThrow(() -> new IllegalArgumentException("Session not found."));
        var ticket = ticketRepository.findById(dto.sessionId())
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found."));
        var room = roomRepository.findById(dto.roomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found."));

        var entity = mapper.ticketCreateRequestDTOConvertToEntity(dto);
        entity.setSession(session);
        entity.setRoom(room);
        entity.setTicket(ticket);

        seatRepository.save(entity);
    }
}
