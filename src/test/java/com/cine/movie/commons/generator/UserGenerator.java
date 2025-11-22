package com.cine.movie.commons.generator;

import com.cine.movie.commons.util.ObjectMapperFactory;
import com.cine.movie.domain.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class UserGenerator {

    private final ObjectMapper mapper;

    public UserGenerator() {
        this.mapper = ObjectMapperFactory.createObjectMapper();
    }

    public UserEntity generateEntity() {
        var resource = new ClassPathResource("json/entity/user.json");
        try {
            return mapper.readValue(resource.getInputStream(), UserEntity.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
