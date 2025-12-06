package com.cine.movie.domain.entity;

import com.cine.movie.domain.entity.enums.RoomAvailability;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.EnumType.STRING;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "rooms")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "is_available", nullable = false)
    @Enumerated(STRING)
    private RoomAvailability isAvailable;
}