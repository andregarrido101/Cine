package com.cine.movie.controller.rest;

import com.cine.movie.commons.generator.JsonGeneratorTest;
import com.cine.movie.domain.dto.sessions.SessionCreateRequestDTO;
import com.cine.movie.domain.entity.SessionEntity;
import com.cine.movie.domain.service.SessionService;
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
class SessionControllerTest {

    private final JsonGeneratorTest jsonGeneratorTest = new JsonGeneratorTest();

    @Mock
    private SessionService service;

    @InjectMocks
    private SessionController controller;

    private List<SessionEntity> sessionEntityList;
    private SessionEntity sessionEntity;
    private SessionCreateRequestDTO sessionCreateRequest;

    @BeforeEach
    void setUp() {
        sessionEntityList = jsonGeneratorTest.generateList(
                "json/entity/sessionList.json",
                SessionEntity.class
        );
        sessionEntity = jsonGeneratorTest.generate(
                "json/request/session.json",
                SessionEntity.class
        );
    }

    @DisplayName("Deve buscar todas as sessões para um filme e retornar 200")
    @Test
    void testGetAllSessionsForAMovieWithSuccessAndReturn200() {
        Long id = 1L;

        when(service.getAllSessionsForAMovie(id))
                .thenReturn(sessionEntityList);

        var result = controller.getAllSessionsForAMovie(id);

        verify(service).getAllSessionsForAMovie(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @DisplayName("Deve criar uma sessão com sucesso e retornar 201")
    @Test
    void testCreateSessionWithSuccessAndReturn201() {
        doNothing().when(service).createSession(sessionCreateRequest);

        var result = controller.createSession(sessionCreateRequest);

        verify(service).createSession(sessionCreateRequest);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
}