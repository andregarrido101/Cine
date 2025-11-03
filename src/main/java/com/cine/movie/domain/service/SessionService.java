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
import static com.cine.movie.domain.entity.enums.RoomAvailability.OCCUPIED_ROOM;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;
    private final SessionMapper mapper;

    public List<SessionEntity> getAllSessions() {
        log.info("Fetching all sessions from the database");
        return sessionRepository.findAll();
    }

    @Transactional
    public void createSession(SessionCreateRequestDTO dto) {
        log.info("Creating a new session in the database");

        var movie = verifyMovieExistence(dto);

        if (Objects.equals(movie.getActiveSession(), AVAILABLE_MOVIE)) {
            throw new IllegalArgumentException("Movie already has an active session.");
        } else {
            movie.setActiveSession(AVAILABLE_MOVIE);
            movieRepository.save(movie);
        }

        var room = verifyRoomAvailability(dto);
        room.setIsAvailable(OCCUPIED_ROOM);

        var entity = mapper.sessionCreateRequestDTOConvertToEntity(dto);
        sessionRepository.save(entity);
    }

    public MovieEntity verifyMovieExistence(SessionCreateRequestDTO dto) {
        var movie = movieRepository.findMovieByTitle(dto.movieTitle());

        if (!Objects.equals(movie.getMovieTitle(), dto.movieTitle())) {
            throw new IllegalArgumentException("Movie does not exist in the database.");
        }

        return movie;
    }

    public RoomEntity verifyRoomAvailability(SessionCreateRequestDTO dto) {
        var room = roomRepository.findRoomByRoomNumber(dto.roomNumber());

        if (Objects.equals(room.getIsAvailable(), OCCUPIED_ROOM)) {
            throw new IllegalArgumentException("Room is not available for the session time.");
        }

        return room;
    }
}