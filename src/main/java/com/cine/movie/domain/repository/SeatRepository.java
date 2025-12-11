package com.cine.movie.domain.repository;

import com.cine.movie.domain.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {

    @Query("""
            SELECT EXISTS (
                SELECT 1
                FROM SeatEntity s
                WHERE seatCode = :seatCode AND session.id = :sessionId
            )
            """)
    Boolean existsBySeatCodeAndSessionId(String seatCode, Long sessionId);
}
