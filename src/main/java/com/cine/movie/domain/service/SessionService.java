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
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
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

        var movie = verifyMovieExistenceAndAvailability(dto);

        var room = verifyRoomExistenceAndAvailability(dto);

        verifySessionTimeConflict(dto, movie);

        var entity = mapper.sessionCreateRequestDTOConvertToEntity(dto);
        entity.setMovie(movie);
        entity.setRoom(room);
        entity.setEndSessionTime(dto.startSessionTime().plusMinutes(movie.getDurationMinutes()));
        sessionRepository.save(entity);
    }

    public MovieEntity verifyMovieExistenceAndAvailability(SessionCreateRequestDTO dto) {
        var movie = movieRepository.findById(dto.movieId())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found in the database."));
        if (Objects.equals(movie.getActiveSession(), UNAVAILABLE_MOVIE)) {
            movie.setActiveSession(AVAILABLE_MOVIE);
            movieRepository.save(movie);
        }
        return movie;
    }

    public RoomEntity verifyRoomExistenceAndAvailability(SessionCreateRequestDTO dto) {
        return roomRepository.findById(dto.roomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found in the database."));
    }

    public void verifySessionTimeConflict(SessionCreateRequestDTO dto, MovieEntity movie) {
        var endSessionTimeCreate = dto.startSessionTime().plusMinutes(movie.getDurationMinutes());

        var session = sessionRepository.findAll(Sort.by("startSessionTime").ascending());

        int lastIndex = session.size() - 1;

        for (int i = 0; i < lastIndex; i++) {
            var firstSessionCheck = i;
            var secondSessionCheck = i + 1;

            var startSessionTimeCreate = dto.startSessionTime();

            var endFirstSessionTimeExisting = session.get(firstSessionCheck).getEndSessionTime();
            var startSecondSessionTimeExisting = session.get(secondSessionCheck).getStartSessionTime();

            if (startSessionTimeCreate.isAfter(endFirstSessionTimeExisting) &&
                endSessionTimeCreate.isBefore(startSecondSessionTimeExisting)) {
                break;
            } else if (secondSessionCheck >= lastIndex) {
                throw new IllegalArgumentException("All time slots are booked for that room; please choose another room or day.");
            }
        }
    }
}