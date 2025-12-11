package com.cine.movie.domain.service;

import com.cine.movie.commons.generator.JsonGeneratorTest;
import com.cine.movie.domain.entity.MovieEntity;
import com.cine.movie.domain.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    private final JsonGeneratorTest jsonGeneratorTest = new JsonGeneratorTest();

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService service;

    private List<MovieEntity> movieEntityList;

    @BeforeEach
    void setUp() {
        movieEntityList = jsonGeneratorTest.generateList(
                "json/entity/movieList.json",
                MovieEntity.class
        );
    }

    @DisplayName("Deve buscar todos os filmes que tenham uma sessão ativa")
    @Test
    void testGetAllMoviesShouldGetAllMoviesThatHasASession() {
        when(movieRepository.findMoviesWithSession())
                .thenReturn(movieEntityList);

        service.getAllMoviesWithASession();

        verify(movieRepository).findMoviesWithSession();
    }
}