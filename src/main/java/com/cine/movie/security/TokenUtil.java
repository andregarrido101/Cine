package com.cine.movie.security;

import com.cine.movie.domain.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class TokenUtil {

    private static final String EMISSOR = "Cine";
    private static final String TOKEN_HEADER = "Bearer ";
    private static final String TOKEN_KEY = "C15PBNabV17XiBcZGG_I0WY1BGWD7lnZGRbrsQYvoP6e9iLJVJhDb0bHr5sBVpHH";
    private static final long UMA_SEMANA = 604800000L;
    private static final long UM_MINUTO = 60 * UMA_SEMANA;

    public AuthToken enodeToken(UserEntity user) {
        var secretKey = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes());
        String tokenJWT = Jwts.builder().setSubject(user.getEmail())
                .setIssuer(EMISSOR)
                .setExpiration(new Date(System.currentTimeMillis() + UM_MINUTO))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
        return new AuthToken(TOKEN_HEADER + tokenJWT);
    }

    public Authentication decodeToken(HttpServletRequest request) {
        var jwtToken = request.getHeader("Authorization");

        if (jwtToken == null || !jwtToken.startsWith(TOKEN_HEADER)) {
            return null;
        }

        jwtToken = jwtToken.replace(TOKEN_HEADER, "");

        Jws<Claims> jwtClaims = Jwts.parserBuilder()
                .setSigningKey(TOKEN_KEY.getBytes())
                .build()
                .parseClaimsJws(jwtToken);

        var user = jwtClaims.getBody().getSubject();
        var emissor = jwtClaims.getBody().getIssuer();
        var validade = jwtClaims.getBody().getExpiration();

        if (!user.isEmpty() && emissor.equals(EMISSOR) && validade.after(new Date(System.currentTimeMillis()))) {
            return new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );
        }

        return null;
    }

}
