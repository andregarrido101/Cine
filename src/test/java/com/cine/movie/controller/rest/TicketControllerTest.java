package com.cine.movie.controller.rest;

import com.cine.movie.commons.generator.JsonGeneratorTest;
import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.cine.movie.domain.entity.TicketEntity;
import com.cine.movie.domain.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    private final JsonGeneratorTest jsonGeneratorTest = new JsonGeneratorTest();

    @Mock
    private TicketService service;

    @InjectMocks
    private TicketController controller;

    private TicketCreateRequestDTO ticketCreateRequest;
    private List<TicketEntity> ticketEntityList;
    private TicketEntity ticketEntity;

    @BeforeEach
    void setUp() {
        ticketEntityList = jsonGeneratorTest.generateList(
                "json/entity/ticketList.json",
                TicketEntity.class
        );
        ticketEntity = jsonGeneratorTest.generate(
                "json/entity/ticket.json",
                TicketEntity.class
        );
    }

    @DisplayName("Deve buscar todos os tickets e retornar 200")
    @Test
    void testGetAllTicketsShouldGetAllTicketsWithSuccess() {
        when(service.getAllTickets()).thenReturn(ticketEntityList);

        var result = controller.getAllTickets();

        verify(service).getAllTickets();

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @DisplayName("Deve criar um ticket com sucesso e retornar 201")
    @Test
    void testBuyTicketShouldCreateATicketWithSuccess() {
        when(service.buyTicket(ticketCreateRequest)).thenReturn(null);

        var result = controller.buyTicket(ticketCreateRequest);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        verify(service).buyTicket(ticketCreateRequest);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
}