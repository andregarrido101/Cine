package com.cine.movie.domain.service;

import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.cine.movie.domain.entity.TicketEntity;
import com.cine.movie.domain.entity.UserEntity;
import com.cine.movie.domain.mapper.SeatMapper;
import com.cine.movie.domain.mapper.TicketMapper;
import com.cine.movie.domain.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final PaymentService paymentService;
    private final TicketMapper ticketMapper;
    private final SeatMapper seatMapper;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final SeatRepository seatRepository;
    private final RoomRepository roomRepository;
    private final SeatService seatService;

    @Transactional
    public TicketEntity buyTicket(TicketCreateRequestDTO dto) {
        log.info("Creating a new ticket");

        paymentService.processPayment();
        verifyUserExistence(dto);
        verifySessionExistence(dto);

        // Verificar se a sala esta ocupada ou retirar roomId daqui

        verifySeatAvailability(dto);
        subtractSeatFromSession(dto);

        var entity = ticketMapper.ticketCreateRequestDTOConvertToEntity(dto);
        var room = roomRepository.findById(dto.roomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found."));
        var user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        var session = sessionRepository.findById(dto.sessionId())
                .orElseThrow(() -> new IllegalArgumentException("Session not found."));

        entity.setRoom(room);
        entity.setUser(user);
        entity.setSession(session);

        var ticketSaved = ticketRepository.save(entity);

        seatService.registrySeatPurchased(dto);

        return ticketSaved;
    }

    private void verifyUserExistence(TicketCreateRequestDTO dto) {
        var user = userRepository.existsById(dto.userId());
        if (!user) {
            throw new IllegalArgumentException("User not found.");
        }
    }

    private void verifySessionExistence(TicketCreateRequestDTO dto) {
        var session = sessionRepository.existsById(dto.sessionId());
        if (!session) {
            throw new IllegalArgumentException("Session not found.");
        }
    }

    private void verifySeatAvailability(TicketCreateRequestDTO dto) {
        var seatAvailable = seatRepository.existsBySeatCodeAndSessionId(dto.seatCode(), dto.sessionId());
        if (seatAvailable) {
            throw new IllegalArgumentException("Seat not available.");
        }
    }

    private void subtractSeatFromSession(TicketCreateRequestDTO dto) {
        sessionRepository.subtractSeatQuantity(dto.sessionId(), 1);
    }

    private void registrySeats(TicketCreateRequestDTO dto) {
        var entity = seatMapper.ticketCreateRequestDTOConvertToEntity(dto);
        seatRepository.save(entity);
    }
}
