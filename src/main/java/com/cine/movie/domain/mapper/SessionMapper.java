package com.cine.movie.domain.mapper;

import com.cine.movie.domain.dto.sessions.SessionCreateRequestDTO;
import com.cine.movie.domain.dto.sessions.SessionResponseDTO;
import com.cine.movie.domain.entity.SessionEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Component
public class SessionMapper {

    public SessionEntity sessionCreateRequestDTOConvertToEntity(SessionCreateRequestDTO dto) {
        if (isNull(dto)) {
            return null;
        }

        var sessionEntity = new SessionEntity();
        sessionEntity.setPricePerSeat(dto.pricePerSeat());
        sessionEntity.setStartSessionTime(dto.startSessionTime());
        sessionEntity.setAvailableSeats(dto.availableSeats());

        return sessionEntity;
    }

    public SessionResponseDTO sessionResponseConvertEntityToDTO(SessionEntity session) {
        return new SessionResponseDTO(session.getMovie().getMovieTitle(), session.getStartSessionTime(), session.getRoom().getRoomNumber());
    }
}