package com.cine.movie.commons.generator;

import com.cine.movie.commons.util.ObjectMapperFactory;
import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class TicketGenerator {

    private final ObjectMapper mapper;

    public TicketGenerator() {
        this.mapper = ObjectMapperFactory.createObjectMapper();
    }

    public TicketCreateRequestDTO generateRequestCreate() {
        var resource = new ClassPathResource("json/request/ticket.json");
        try {
            return mapper.readValue(resource.getFile(), TicketCreateRequestDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
