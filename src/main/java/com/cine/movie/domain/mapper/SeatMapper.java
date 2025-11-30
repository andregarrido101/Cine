package com.cine.movie.domain.mapper;

import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.cine.movie.domain.entity.SeatEntity;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class SeatMapper {

    public SeatEntity ticketCreateRequestDTOConvertToEntity(TicketCreateRequestDTO dto) {
        if (isNull(dto)) {
            return null;
        }

        var seatEntity = new SeatEntity();
        seatEntity.setSeatCode(dto.seatCode());

        return seatEntity;
    }
}


