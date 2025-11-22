package com.cine.movie.domain.service;

import com.cine.movie.commons.generator.JsonGenerator;
import com.cine.movie.commons.generator.TicketGenerator;
import com.cine.movie.commons.generator.UserGenerator;
import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.cine.movie.domain.entity.SeatEntity;
import com.cine.movie.domain.entity.SessionEntity;
import com.cine.movie.domain.entity.TicketEntity;
import com.cine.movie.domain.entity.UserEntity;
import com.cine.movie.domain.mapper.TicketMapper;
import com.cine.movie.domain.repository.SeatRepository;
import com.cine.movie.domain.repository.SessionRepository;
import com.cine.movie.domain.repository.TicketRepository;
import com.cine.movie.domain.repository.UserRepository;
import org.apache.catalina.User;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    /*
    private final TicketGenerator ticketGenerator = new TicketGenerator();
    private final UserGenerator userGenerator = new UserGenerator();
     */
    private final JsonGenerator jsonGenerator = new JsonGenerator();

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
    private SeatEntity seatEntity;

    @BeforeEach
    void setUp() {
        /*
        userEntity = userGenerator.generateEntity();
        ticketCreateRequest = ticketGenerator.generateRequestCreate();

         */
        userEntity = jsonGenerator.generate(
                "json/entity/user.json",
                UserEntity.class
        );
        ticketCreateRequest = jsonGenerator.generate(
                "json/request/ticket.json",
                TicketCreateRequestDTO.class
        );
        sessionEntity = jsonGenerator.generate(
                "json/entity/session.json",
                SessionEntity.class
        );
        ticketEntity = jsonGenerator.generate(
                "/json/entity/ticket.json",
                TicketEntity.class
        );
        seatEntity = jsonGenerator.generate(
                "json/entity/seat.json",
                SeatEntity.class
        );
    }

    @Test
    void shouldCreateATicket() {

        when(userRepository.findById(ticketCreateRequest.userId()))
                .thenReturn(Optional.of(userEntity));
        when(sessionRepository.findById(ticketCreateRequest.sessionId()))
                .thenReturn(Optional.of(sessionEntity));
        when(ticketMapper.ticketCreateRequestDTOConvertToEntity(ticketCreateRequest))
                .thenReturn(ticketEntity);
        when(seatRepository.existsBySeatCodeAndSessionId(ticketCreateRequest.seatCode(), ticketCreateRequest.sessionId()))
                .thenReturn(false);

        service.buyTicket(ticketCreateRequest);

        verify(userRepository).findById(ticketCreateRequest.userId());
        verify(sessionRepository).findById(ticketCreateRequest.sessionId());

        ArgumentCaptor<TicketEntity> captor = ArgumentCaptor.forClass(TicketEntity.class);
        verify(ticketRepository).save(captor.capture());

        TicketEntity savedTicket = captor.getValue();
        assertEquals(userEntity, savedTicket.getUser());

    }
}