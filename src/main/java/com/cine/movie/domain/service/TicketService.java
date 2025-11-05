package com.cine.movie.domain.service;

import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.cine.movie.domain.entity.RoomEntity;
import com.cine.movie.domain.entity.SessionEntity;
import com.cine.movie.domain.entity.TicketEntity;
import com.cine.movie.domain.entity.UserEntity;
import com.cine.movie.domain.mapper.SeatMapper;
import com.cine.movie.domain.mapper.TicketMapper;
import com.cine.movie.domain.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.cine.movie.domain.entity.enums.RoomAvailability.OCCUPIED_ROOM;


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

        var user = verifyUserExistence(dto);
        var session = verifySessionExistenceAndAvailability(dto);

        verifySeatAvailability(dto);
        subtractSeatFromSession(dto);

        var entity = ticketMapper.ticketCreateRequestDTOConvertToEntity(dto);
        entity.setUser(user);
        entity.setSession(session);

        var ticketSaved = ticketRepository.save(entity);

        seatService.registrySeatPurchased(dto);

        return ticketSaved;
    }

    private UserEntity verifyUserExistence(TicketCreateRequestDTO dto) {
        return userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    private SessionEntity verifySessionExistenceAndAvailability(TicketCreateRequestDTO dto) {
        var session = sessionRepository.findById(dto.sessionId())
                .orElseThrow(() -> new IllegalArgumentException("Session not found."));
        if (session.getAvailableSeats() == 0) {
            throw new IllegalArgumentException("No available seats for this session.");
        }
        return session;
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

}
