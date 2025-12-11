package com.cine.movie.domain.service;

import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.cine.movie.domain.dto.tickets.TicketResponseDTO;
import com.cine.movie.domain.entity.SessionEntity;
import com.cine.movie.domain.entity.TicketEntity;
import com.cine.movie.domain.entity.UserEntity;
import com.cine.movie.domain.mapper.SessionMapper;
import com.cine.movie.domain.mapper.TicketMapper;
import com.cine.movie.domain.mapper.UserMapper;
import com.cine.movie.domain.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketService {

    private final PaymentService paymentService;
    private final SeatService seatService;

    private final TicketMapper ticketMapper;
    private final UserMapper userMapper;
    private final SessionMapper sessionMapper;

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    public List<TicketEntity> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Transactional
    public TicketResponseDTO buyTicket(TicketCreateRequestDTO dto) {
        log.info("Creating a new ticket");

        paymentService.processPayment();

        var userEntity = verifyUserExistence(dto);
        var userDTO = userMapper.userResponseConvertEntityToDTO(userEntity);

        var sessionEntity = verifySessionExistenceAndAvailability(dto);
        var sessionDTO = sessionMapper.sessionResponseConvertEntityToDTO(sessionEntity);

        verifySeatAvailability(dto);
        subtractSeatFromSession(dto);

        var ticketEntity = ticketMapper.ticketCreateRequestDTOConvertToEntity(dto);
        ticketEntity.setUser(userEntity);
        ticketEntity.setSession(sessionEntity);

        var ticketSaved = ticketRepository.save(ticketEntity);

        seatService.registrySeatPurchased(ticketSaved, dto);

        return new TicketResponseDTO(userDTO.username(), sessionDTO.startSessionTime(), sessionDTO.roomNumber(), dto.seatCode());
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
