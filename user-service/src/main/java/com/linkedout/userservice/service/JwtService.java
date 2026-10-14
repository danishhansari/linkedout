package com.linkedout.userservice.service;

import java.util.UUID;

public interface JwtService {
    String generateAccessToken(UUID userId, String email);
    String generateRefreshToken(UUID userId);
}
