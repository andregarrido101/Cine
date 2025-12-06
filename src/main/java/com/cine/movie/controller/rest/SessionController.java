package com.cine.movie.controller.rest;

import com.cine.movie.controller.api.ISessionController;
import com.cine.movie.domain.dto.sessions.SessionCreateRequestDTO;
import com.cine.movie.domain.entity.SessionEntity;
import com.cine.movie.domain.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SessionController implements ISessionController {

    private final SessionService sessionService;

    @Override
    public ResponseEntity<List<SessionEntity>> getAllSessionsForAMovie(Long id) {
        List<SessionEntity> sessions = sessionService.getAllSessionsForAMovie(id);
        return ResponseEntity.status(HttpStatus.OK).body(sessions);
    }

    @Override
    public ResponseEntity<Void> createSession(SessionCreateRequestDTO sessionCreateRequestDTO) {
        sessionService.createSession(sessionCreateRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}