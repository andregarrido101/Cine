package com.cine.movie.domain.repository;

import com.cine.movie.domain.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    @Query("""
            SELECT r\s
            FROM RoomEntity r\s
            WHERE roomNumber = :roomNumber
           \s""")
    RoomEntity findRoomByRoomNumber(@Param("roomNumber") Integer roomNumber);
}
