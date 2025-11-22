package com.cine.movie.domain.service;

import com.cine.movie.domain.dto.sessions.SessionCreateRequestDTO;
import com.cine.movie.domain.entity.MovieEntity;
import com.cine.movie.domain.entity.RoomEntity;
import com.cine.movie.domain.entity.SessionEntity;
import com.cine.movie.domain.entity.enums.MovieAvailability;
import com.cine.movie.domain.entity.enums.RoomAvailability;
import com.cine.movie.domain.mapper.SessionMapper;
import com.cine.movie.domain.repository.MovieRepository;
import com.cine.movie.domain.repository.RoomRepository;
import com.cine.movie.domain.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.cine.movie.domain.entity.enums.MovieAvailability.AVAILABLE_MOVIE;
import static com.cine.movie.domain.entity.enums.MovieAvailability.UNAVAILABLE_MOVIE;
import static com.cine.movie.domain.entity.enums.RoomAvailability.OCCUPIED_ROOM;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;
    private final SessionMapper mapper;

    public List<SessionEntity> getAllSessionsForAMovie(Long id) {
        log.info("Fetching all sessions for a specific movie from the database");
        return sessionRepository.findByMovieId(id);
    }

    @Transactional
    public void createSession(SessionCreateRequestDTO dto) {
        log.info("Creating a new session in the database");

        var movie = verifyMovieExistence(dto);

        if (Objects.equals(movie.getActiveSession(), UNAVAILABLE_MOVIE)) {
            movie.setActiveSession(AVAILABLE_MOVIE);
            movieRepository.save(movie);
        }

        var room = verifyRoomExistence(dto);

        if (Objects.equals(room.getIsAvailable(), OCCUPIED_ROOM)) {
            throw new IllegalArgumentException("Room is not available for the session time.");
        } else {
            room.setIsAvailable(OCCUPIED_ROOM);
            roomRepository.save(room);
        }

        // Verificar se já existe uma sessão no mesmo horário para a minha sala, calcular com base no tempo de duração
        // do filme e o tempo das pessoas saírem da sala e a limpeza da sala antes da próxima sessão começar.

        var entity = mapper.sessionCreateRequestDTOConvertToEntity(dto);
        entity.setMovie(movie);
        entity.setRoom(room);
        sessionRepository.save(entity);

        // Adicionar lógica para liberar a sala (setar room para disponível e excluir a sessão) após a sessão acabar
    }

    public MovieEntity verifyMovieExistence(SessionCreateRequestDTO dto) {
        return movieRepository.findById(dto.movieId())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found in the database."));
    }

    public RoomEntity verifyRoomExistence(SessionCreateRequestDTO dto) {
        return roomRepository.findById(dto.roomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found in the database."));
    }
}