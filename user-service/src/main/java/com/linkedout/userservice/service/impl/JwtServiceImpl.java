package com.linkedout.userservice.service.impl;

import com.linkedout.userservice.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private Key getSignedKey() {
        byte [] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    /*
     * Generated access token
     * @param userId
     * @param email
     * @return
     */
    @Override
    public String generateAccessToken(UUID userId, String email) {
        return Jwts.builder()
                .claim("userId", userId)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignedKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /*
     * Generated refresh token
     *
     * Use to get new access token when access expired
     * Server validates and return new access token
     * client sends refresh token to /auth/refresh-token
     * @param userId
     * @return
     */
    @Override
    public String generateRefreshToken(UUID userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .subject(String.valueOf(userId))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(getSignedKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
