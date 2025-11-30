package com.cine.movie.domain.service;

import com.cine.movie.commons.generator.JsonGeneratorTest;
import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.cine.movie.domain.entity.SessionEntity;
import com.cine.movie.domain.entity.TicketEntity;
import com.cine.movie.domain.entity.UserEntity;
import com.cine.movie.domain.mapper.TicketMapper;
import com.cine.movie.domain.repository.SeatRepository;
import com.cine.movie.domain.repository.SessionRepository;
import com.cine.movie.domain.repository.TicketRepository;
import com.cine.movie.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    private final JsonGeneratorTest jsonGeneratorTest = new JsonGeneratorTest();

    @Mock
    private UserRepository userRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private SeatService seatService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketMapper ticketMapper;

    @InjectMocks
    private TicketService service;

    private UserEntity userEntity;
    private TicketEntity ticketEntity;
    private TicketCreateRequestDTO ticketCreateRequest;
    private SessionEntity sessionEntity;
    private List<TicketEntity> ticketEntityList;

    @BeforeEach
    void setUp() {
        userEntity = jsonGeneratorTest.generate(
                "json/entity/user.json",
                UserEntity.class
        );
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
        ticketEntityList = jsonGeneratorTest.generateList(
                "json/entity/ticketList.json",
                TicketEntity.class
        );
    }

    @DisplayName("Deve buscar todos os tickets com sucesso")
    @Test
    void testGetAllTicketsShouldGetTicketsWithSuccess() {
        when(ticketRepository.findAll()).thenReturn(ticketEntityList);

        var result = service.getAllTickets();

        verify(ticketRepository).findAll();

        assertEquals(ticketEntityList.getFirst(), result.getFirst());
        assertEquals(ticketEntityList.get(1), result.get(1));
        assertEquals(ticketEntityList.get(2), result.get(2));
        assertEquals(ticketEntityList.get(3), result.get(3));
        assertEquals(ticketEntityList.get(4), result.get(4));
    }

    @DisplayName("Deve criar um ticket com sucesso")
    @Test
    void testBuyTicketWithSuccess() {

        when(userRepository.findById(ticketCreateRequest.userId()))
                .thenReturn(Optional.of(userEntity));
        when(sessionRepository.findById(ticketCreateRequest.sessionId()))
                .thenReturn(Optional.of(sessionEntity));
        when(ticketMapper.ticketCreateRequestDTOConvertToEntity(ticketCreateRequest))
                .thenReturn(ticketEntity);
        when(seatRepository.existsBySeatCodeAndSessionId(ticketCreateRequest.seatCode(), ticketCreateRequest.sessionId()))
                .thenReturn(false);

        service.buyTicket(ticketCreateRequest);

        verify(paymentService).processPayment();
        verify(seatService).registrySeatPurchased(ticketCreateRequest);

        verify(userRepository).findById(ticketCreateRequest.userId());
        verify(sessionRepository).findById(ticketCreateRequest.sessionId());
        verify(seatRepository).existsBySeatCodeAndSessionId(ticketCreateRequest.seatCode(), ticketCreateRequest.sessionId());

        ArgumentCaptor<TicketEntity> captor = ArgumentCaptor.forClass(TicketEntity.class);
        verify(ticketRepository).save(captor.capture());

        TicketEntity savedTicket = captor.getValue();
        assertEquals(userEntity, savedTicket.getUser());
        assertEquals(sessionEntity, savedTicket.getSession());
        assertEquals(ticketEntity.getSeatCode(), savedTicket.getSeatCode());

    }

    @DisplayName("Deve lançar uma erro se o usuário não existir")
    @Test
    void testBuyTicketShouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(ticketCreateRequest.userId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.buyTicket(ticketCreateRequest));

        verify(userRepository).findById(ticketCreateRequest.userId());
        verify(ticketRepository, never()).save(any());
    }

    @DisplayName("Deve lançar um erro se a sessão não existir")
    @Test
    void testBuyTicketShouldThrowExceptionWhenSessionNotFound() {
        when(userRepository.findById(ticketCreateRequest.userId()))
                .thenReturn(Optional.of(userEntity));
        when(sessionRepository.findById(ticketCreateRequest.sessionId()))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.buyTicket(ticketCreateRequest));

        verify(userRepository).findById(ticketCreateRequest.userId());
        verify(sessionRepository).findById(ticketCreateRequest.sessionId());
        verify(ticketRepository, never()).save(any());
    }

    @DisplayName("Deve lançar um erro se o assento não estiver disponível")
    @Test
    void testBuyTicketShouldThrowExceptionWhenSeatNotAvailable() {
        when(userRepository.findById(ticketCreateRequest.userId()))
                .thenReturn(Optional.of(userEntity));
        when(sessionRepository.findById(ticketCreateRequest.sessionId()))
                .thenReturn(Optional.of(sessionEntity));
        when(seatRepository.existsBySeatCodeAndSessionId(ticketCreateRequest.seatCode(), ticketCreateRequest.sessionId()))
                .thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.buyTicket(ticketCreateRequest));

        verify(userRepository).findById(ticketCreateRequest.userId());
        verify(sessionRepository).findById(ticketCreateRequest.sessionId());
        verify(seatRepository).existsBySeatCodeAndSessionId(ticketCreateRequest.seatCode(), ticketCreateRequest.sessionId());
        verify(ticketRepository, never()).save(any());
    }
}