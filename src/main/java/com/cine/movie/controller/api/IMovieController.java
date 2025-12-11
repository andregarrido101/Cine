package com.cine.movie.controller.api;

import com.cine.movie.domain.entity.MovieEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/v1/movies")
@Tag(name = "Room Management", description = "Endpoints for managing rooms")
public interface IMovieController {

    @Operation(summary = "Get all movies", description = "Retrieves a list of all movies available.")
    @ApiResponses(value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved list of movies",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No movies found",
                            content = @Content
                    )
            }
    )
    @GetMapping
    ResponseEntity<List<MovieEntity>> getAllMoviesWithASession();

}
