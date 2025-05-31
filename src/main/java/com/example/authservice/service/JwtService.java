package com.example.authservice.service;

import com.example.authservice.config.Config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtService {
    private static final String SECRET = Config.JWT_SECRET;
    private static final int EXPIRATION_MINUTES = 24 * 60;

    private static Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public static String generateToken(String email) {

        Instant now = Instant.now();
        Instant expiry = now.plus(EXPIRATION_MINUTES, ChronoUnit.MINUTES);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry) )
                .signWith(getSigningKey())
                .compact();
    }

    public static String generateTemporalToken() {
        Instant now = Instant.now();
        Instant expiry = now.plus(1, ChronoUnit.MINUTES);

        return Jwts.builder()
                .setSubject("admin@winwin.com")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry) )
                .signWith(getSigningKey())
                .compact();
    }

    public static Instant getExpirationDate() {
        Instant now = Instant.now();
        return  now.plus(EXPIRATION_MINUTES, ChronoUnit.MINUTES);
    }
} 