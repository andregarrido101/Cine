package com.cine.movie.security;

import com.cine.movie.domain.dto.ErroDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class CineFilter extends OncePerRequestFilter {

    private final TokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Rotas públicas — não validar token
        if (isPublicRoute(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Validação normal de token
        String authorization = request.getHeader("Authorization");

        if (authorization != null) {
            var auth = tokenUtil.decodeToken(request);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                var error = new ErroDTO();
                response.setStatus(error.getStatus());
                response.setContentType("application/json");
                ObjectMapper mapper = new ObjectMapper();
                response.getWriter().print(mapper.writeValueAsString(error));
                response.getWriter().flush();
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicRoute(String path) {
        return path.startsWith("/api/v1/users")
                || path.startsWith("/api/v1/auth/login")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs");
    }
}
