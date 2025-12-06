package com.cine.movie.domain.service;

import com.cine.movie.domain.entity.SessionEntity;
import com.cine.movie.domain.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionCleanupScheduler {

    private final SessionRepository sessionRepository;

    @Scheduled(fixedRate = 60_000)
    public void deleteCompletedSessions() {
        log.info("Deleting completed sessions from the database");

        LocalDateTime now = LocalDateTime.now();

        List<SessionEntity> expiredSessions = sessionRepository.findAll().stream()
                .filter(session -> Duration.between(session.getEndSessionTime(), now).toMinutes() >= 0)
                .toList();

        for (SessionEntity session : expiredSessions) {
            sessionRepository.delete(session);
        }
    }
}
