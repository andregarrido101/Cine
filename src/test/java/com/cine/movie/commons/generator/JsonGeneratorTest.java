package com.cine.movie.commons.generator;

import com.cine.movie.commons.util.ObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

public class JsonGeneratorTest {

    private final ObjectMapper mapper;

    public JsonGeneratorTest() {
        this.mapper = ObjectMapperFactory.createObjectMapper();
    }

    public <T> T generate(String path, Class<T> clazz) {
        var resource = new ClassPathResource(path);
        try {
            return mapper.readValue(resource.getFile(), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> generateList(String path, Class<T> clazz) {
        var resource = new ClassPathResource(path);
        try {
            return mapper.readValue(
                    resource.getFile(),
                    mapper.getTypeFactory().constructCollectionType(List.class, clazz)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
