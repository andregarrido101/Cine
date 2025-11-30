package com.cine.movie.domain.service;

import com.cine.movie.commons.generator.JsonGeneratorTest;
import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.cine.movie.domain.entity.SeatEntity;
import com.cine.movie.domain.entity.SessionEntity;
import com.cine.movie.domain.entity.TicketEntity;
import com.cine.movie.domain.mapper.SeatMapper;
import com.cine.movie.domain.repository.SeatRepository;
import com.cine.movie.domain.repository.SessionRepository;
import com.cine.movie.domain.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    private final JsonGeneratorTest jsonGeneratorTest = new JsonGeneratorTest();

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private SeatMapper mapper;

    @InjectMocks
    private SeatService service;

    private TicketCreateRequestDTO ticketCreateRequest;
    private SessionEntity sessionEntity;
    private TicketEntity ticketEntity;
    private SeatEntity seatEntity;

    @BeforeEach
    void setUp() {
        ticketCreateRequest = jsonGeneratorTest.generate(
                "json/request/ticket.json",
                TicketCreateRequestDTO.class
        );
        sessionEntity = jsonGeneratorTest.generate(
                "json/entity/session.json",
                SessionEntity.class
        );
        ticketEntity = jsonGeneratorTest.generate(
                "json/entity/ticket.json",
                TicketEntity.class
        );
        seatEntity = jsonGeneratorTest.generate(
                "json/entity/seat.json",
                SeatEntity.class
        );
    }

    @DisplayName("Deve registrar o assento comprado com sucesso")
    @Test
    void testRegistrySeatPurchasedWithSuccess() {
        when(sessionRepository.findById(ticketCreateRequest.sessionId()))
                .thenReturn(Optional.of(sessionEntity));
        when(ticketRepository.findById(ticketCreateRequest.sessionId()))
                .thenReturn(Optional.of(ticketEntity));
        when(mapper.ticketCreateRequestDTOConvertToEntity(ticketCreateRequest))
                .thenReturn(seatEntity);

        service.registrySeatPurchased(ticketCreateRequest);

        verify(sessionRepository).findById(ticketCreateRequest.sessionId());
        verify(ticketRepository).findById(ticketCreateRequest.sessionId());

        ArgumentCaptor<SeatEntity> captor = ArgumentCaptor.forClass(SeatEntity.class);
        verify(seatRepository).save(captor.capture());

        SeatEntity savedSeat = captor.getValue();
        assertEquals(seatEntity.getSeatCode(), savedSeat.getSeatCode());
        assertEquals(seatEntity.getSession(), savedSeat.getSession());
        assertEquals(seatEntity.getTicket(), savedSeat.getTicket());
    }

    @DisplayName("Deve lançar um erro quando a sessão não existir")
    @Test
    void testVerifySessionExistenceShouldThrowExceptionWhenSessionNotFound() {
        when(sessionRepository.findById(ticketCreateRequest.sessionId()))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.verifySessionExistence(ticketCreateRequest));

        verify(sessionRepository).findById(ticketCreateRequest.sessionId());
        verify(seatRepository, never()).save(any());
    }

    @DisplayName("Deve lançar um erro quando o ticket não existir")
    @Test
    void testVerifyTicketExistenceShouldThrowExceptionWhenSesssionNotFound() {
        when(ticketRepository.findById(ticketCreateRequest.sessionId()))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.verifyTicketExistence(ticketCreateRequest));

        verify(ticketRepository).findById(ticketCreateRequest.sessionId());
        verify(ticketRepository, never()).save(any());
    }
}