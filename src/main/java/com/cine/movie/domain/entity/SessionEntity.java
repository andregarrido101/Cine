package com.cine.movie.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "price_per_seat")
    private Double pricePerSeat;

    @Column(name = "session_time")
    private String sessionTime;

    @Column(name = "room_number")
    private int roomNumber;

    @Column(name = "available_seats")
       private int availableSeats;
}