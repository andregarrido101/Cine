package com.cine.movie.domain.service;

import com.cine.movie.domain.entity.MovieEntity;
import com.cine.movie.domain.repository.MovieRepository;
import com.cine.movie.domain.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<MovieEntity> getAllMoviesWithASession() {
        log.info("Fetching all movie that have a session from the database");
        return movieRepository.findMoviesWithSession();
    }
}