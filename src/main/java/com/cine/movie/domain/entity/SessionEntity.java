package com.cine.movie.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "sessions", schema = "cine")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private MovieEntity movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private RoomEntity room;

    @Column(name = "price_per_seat")
    private Double pricePerSeat;

    @Column(name = "start_session_time")
    private LocalDateTime startSessionTime;

    @Column(name = "end_session_time")
    private LocalDateTime endSessionTime;

    @Column(name = "available_seats")
    private int availableSeats;
}