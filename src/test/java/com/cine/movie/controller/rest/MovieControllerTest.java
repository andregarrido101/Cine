package com.cine.movie.controller.rest;

import com.cine.movie.domain.service.MovieService;
import org.hibernate.dialect.function.array.H2ArrayContainsFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    @Mock
    private MovieService service;

    @InjectMocks
    private MovieController controller;

    @DisplayName("Deve buscar todos os filmes que tem uma sessão com sucesso e retornar 200")
    @Test
    void testGetAllMoviesThatHasASessionWithSuccessAndReturn200() {
        when(service.getAllMoviesWithASession()).thenReturn(null);

        var result = controller.getAllMoviesWithASession();

        verify(service).getAllMoviesWithASession();

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}