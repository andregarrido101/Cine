package com.cine.movie.domain.service;

import com.cine.movie.domain.dto.http.ClientResponseDTO;
import com.cine.movie.domain.dto.tickets.TicketCreateRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.lang.String.valueOf;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ObjectMapper mapper;

    public void processPayment() {
        try {

            var client = HttpClient.newHttpClient();

            var request = HttpRequest.newBuilder()
                    .uri(URI.create("https://zsy6tx7aql.execute-api.sa-east-1.amazonaws.com/authorizer"))
                    .header("Accept", "application/json")
                    .GET()
                    .timeout(Duration.ofSeconds(30))
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            var clientResponse = mapper.readValue(response.body(), ClientResponseDTO.class);

            if (response.statusCode() != 200
                || clientResponse.status().equalsIgnoreCase("fail")
                || isFalse(clientResponse.data().authorized())) {
                log.warn("Payment authorization failed: {}", clientResponse);
                throw new RuntimeException("Payment not authorized by external authorizing");
            }

            log.debug("Payment authorized successfully: {}", valueOf(clientResponse));

        } catch (Exception e) {
            throw new RuntimeException("External authorization failed");
        }
    }
}
