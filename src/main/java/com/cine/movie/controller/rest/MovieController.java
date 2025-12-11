package com.cine.movie.controller.rest;

import com.cine.movie.controller.api.IMovieController;
import com.cine.movie.domain.entity.MovieEntity;
import com.cine.movie.domain.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController implements IMovieController {

    private final MovieService movieService;

    @Override
    public ResponseEntity<List<MovieEntity>> getAllMoviesWithASession() {
        List<MovieEntity> movies = movieService.getAllMoviesWithASession();
        return ResponseEntity.status(HttpStatus.OK).body(movies);
    }
}
