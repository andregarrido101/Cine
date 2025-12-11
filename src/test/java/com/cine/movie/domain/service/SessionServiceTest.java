package com.cine.movie.domain.service;

import com.cine.movie.commons.generator.JsonGeneratorTest;
import com.cine.movie.domain.dto.sessions.SessionCreateRequestDTO;
import com.cine.movie.domain.entity.MovieEntity;
import com.cine.movie.domain.entity.RoomEntity;
import com.cine.movie.domain.entity.SessionEntity;
import com.cine.movie.domain.mapper.SessionMapper;
import com.cine.movie.domain.repository.MovieRepository;
import com.cine.movie.domain.repository.RoomRepository;
import com.cine.movie.domain.repository.SessionRepository;
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
class SessionServiceTest {

    private final JsonGeneratorTest jsonGeneratorTest = new JsonGeneratorTest();

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SessionMapper sessionMapper;

    @InjectMocks
    private SessionService service;

    private MovieEntity movieEntity;
    private RoomEntity roomEntity;
    private SessionCreateRequestDTO sessionCreateRequest;
    private SessionEntity sessionEntity;

    @BeforeEach
    void setUp() {
        sessionCreateRequest = jsonGeneratorTest.generate(
                "json/request/session.json",
                SessionCreateRequestDTO.class
        );
        movieEntity = jsonGeneratorTest.generate(
                "json/entity/movie.json",
                MovieEntity.class
        );
        roomEntity = jsonGeneratorTest.generate(
                "json/entity/room.json",
                RoomEntity.class
        );
        sessionEntity =  jsonGeneratorTest.generate(
                "json/entity/session.json",
                SessionEntity.class
        );
    }

    @DisplayName("Deve buscar todas as sessões para um filme específico")
    @Test
    void testGetAllSessionsForAMovieWithSuccess() {
        when(sessionRepository.findByMovieId(sessionCreateRequest.movieId()))
                .thenReturn(List.of(sessionEntity));

        service.getAllSessionsForAMovie(sessionCreateRequest.movieId());

        verify(sessionRepository).findByMovieId(sessionCreateRequest.movieId());
    }

    @DisplayName("Deve criar uma sessão com sucesso")
    @Test
    void testCreateSessionShouldCreateSessionWithSuccess() {
        when(movieRepository.findById(sessionCreateRequest.movieId()))
                .thenReturn(Optional.of(movieEntity));
        when(roomRepository.findById(sessionCreateRequest.roomId()))
                .thenReturn(Optional.of(roomEntity));
        when(sessionMapper.sessionCreateRequestDTOConvertToEntity(sessionCreateRequest))
                .thenReturn(sessionEntity);

        service.createSession(sessionCreateRequest);

        verify(movieRepository).findById(sessionCreateRequest.movieId());
        verify(roomRepository).findById(sessionCreateRequest.roomId());

        ArgumentCaptor<SessionEntity> captor = ArgumentCaptor.forClass(SessionEntity.class);
        verify(sessionRepository).save(captor.capture());

        SessionEntity savedSession = captor.getValue();
        assertEquals(roomEntity, savedSession.getRoom());
        assertEquals(movieEntity, savedSession.getMovie());
        assertEquals(sessionEntity.getPricePerSeat(), savedSession.getPricePerSeat());
        assertEquals(sessionEntity.getStartSessionTime(), savedSession.getStartSessionTime());
        assertEquals(sessionEntity.getAvailableSeats(), savedSession.getAvailableSeats());
    }

    @DisplayName("Deve lançar um erro quando o filme não existir")
    @Test
    void testCreateSessionShouldThrowExceptionWhenMovieNotFound() {
        when(movieRepository.findById(sessionCreateRequest.movieId()))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.createSession(sessionCreateRequest));

        verify(movieRepository).findById(sessionCreateRequest.movieId());
        verify(sessionRepository, never()).save(any());
    }

    @DisplayName("Deve lançar um erro quando a sala não existir")
    @Test
    void testCreateSessionShouldThrowExceptionWhenRoomNotFound() {
        when(movieRepository.findById(sessionCreateRequest.movieId()))
                .thenReturn(Optional.of(movieEntity));
        when(roomRepository.findById(sessionCreateRequest.roomId()))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.createSession(sessionCreateRequest));

        verify(movieRepository).findById(sessionCreateRequest.movieId());
        verify(roomRepository).findById(sessionCreateRequest.roomId());
        verify(sessionRepository, never()).save(any());
    }
}