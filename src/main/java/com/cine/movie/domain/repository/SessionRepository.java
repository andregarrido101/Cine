package com.cine.movie.domain.repository;

import com.cine.movie.domain.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

    @Query("""
            SELECT s\s
            FROM SessionEntity s\s
            WHERE s.movie.id = :id\s
            """)
    List<SessionEntity> findByMovieId(@Param("id") Long roomId);

    @Modifying
    @Query("""
            UPDATE SessionEntity s\s
            SET s.availableSeats = s.availableSeats - :quantity\s
            """)
    void subtractSeatQuantity(@Param("quantity") Long sessionId, Integer quantity);
}
