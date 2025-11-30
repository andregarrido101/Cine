package com.cine.movie.domain.mapper;

import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.cine.movie.domain.entity.TicketEntity;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class TicketMapper {

    public TicketEntity ticketCreateRequestDTOConvertToEntity(TicketCreateRequestDTO dto) {
        if (isNull(dto)) {
            return null;
        }

        var ticketEntity = new TicketEntity();
        ticketEntity.setSeatCode(dto.seatCode());

        return ticketEntity;
    }
}
