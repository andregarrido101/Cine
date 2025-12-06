package com.cine.movie.domain.repository;

import com.cine.movie.domain.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    @Query("""
            SELECT m\s
            FROM MovieEntity m\s
            WHERE m.movieTitle = :movieTitle
           \s""")
    MovieEntity findMovieByTitle(@Param("movieTitle") String movieTitle);

    @Query("""
            SELECT m\s
            FROM MovieEntity m\s
            WHERE m.activeSession = com.cine.movie.domain.entity.enums.MovieAvailability.AVAILABLE_MOVIE
            \s""")
    List<MovieEntity> findMoviesWithSession();

    MovieEntity findMovieById(Long id);
}