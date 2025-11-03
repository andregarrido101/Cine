package com.cine.movie.domain.mapper;

import com.cine.movie.domain.dto.sessions.SessionCreateRequestDTO;
import com.cine.movie.domain.entity.SessionEntity;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class SessionMapper {

    public SessionEntity sessionCreateRequestDTOConvertToEntity(SessionCreateRequestDTO dto) {
        if (isNull(dto)) {
            return null;
        }

        var sessionEntity = new SessionEntity();
        sessionEntity.setMovieName(dto.movieTitle());
        sessionEntity.setPricePerSeat(dto.pricePerSeat());
        sessionEntity.setSessionTime(dto.sessionTime());
        sessionEntity.setRoomNumber(dto.roomNumber());
        sessionEntity.setAvailableSeats(dto.availableSeats());

        return sessionEntity;
    }
}