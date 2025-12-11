package com.cine.movie.domain.entity;

import com.cine.movie.domain.entity.enums.MovieAvailability;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.EnumType.STRING;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "movies", schema = "cine")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String movieTitle;

    @Column(name = "synopsis")
    private String synopsis;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "genre")
    private String genre;

    @Column(name = "director")
    private String director;

    @Column(name = "duration_minutes")
    private int durationMinutes;

    @Column(name = "active_session")
    @Enumerated(STRING)
    private MovieAvailability activeSession;
}
