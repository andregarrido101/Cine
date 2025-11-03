package com.cine.movie.domain.service;

import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.cine.movie.domain.entity.TicketEntity;
import com.cine.movie.domain.entity.UserEntity;
import com.cine.movie.domain.mapper.TicketMapper;
import com.cine.movie.domain.repository.SessionRepository;
import com.cine.movie.domain.repository.TicketRepository;
import com.cine.movie.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final PaymentService paymentService;
    private final TicketMapper mapper;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Transactional
    public ResponseEntity<TicketEntity> buyTicket(TicketCreateRequestDTO dto) {
        log.info("Creating a new ticket");

        // Processar o pagamento
        paymentService.processPayment();

        // Verificar se o user existe
        verifyUserExistence(dto);

        // Verificar se a sessão existe
        verifySessionExistence(dto);

        // Verificar se os assentos ainda estão disponíveis


        // Subtrair da entidade sessão o assento comprado

        // Atualizar os assentos

        // Salvar o ticket

        var entity = mapper.ticketCreateRequestDTOConvertToEntity(dto);
        ticketRepository.save(entity);

        return null;
    }

    private void verifyUserExistence(TicketCreateRequestDTO dto) {
        var user = userRepository.existsById(dto.userId());
        if (!user) {
            throw new IllegalArgumentException("User not found.");
        }
    }

    private void verifySessionExistence(TicketCreateRequestDTO dto) {
        var session = sessionRepository.existsById(dto.sessionId());
        if (!session) {
            throw new IllegalArgumentException("Session not found.");
        }
    }

    private void verifySeatAvailability(TicketCreateRequestDTO dto) {
        var
    }
}
