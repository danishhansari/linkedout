package com.linkedout.userservice.service.impl;

import com.linkedout.userservice.assembler.AuthAssembler;
import com.linkedout.userservice.dto.AuthDTO;
import com.linkedout.userservice.entity.User;
import com.linkedout.userservice.event.UserCreatedEvent;
import com.linkedout.userservice.pojo.LoginPojo;
import com.linkedout.userservice.pojo.RegisterPojo;
import com.linkedout.userservice.repository.UserRepository;
import com.linkedout.userservice.service.AuthService;
import com.linkedout.userservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder  passwordEncoder;
    private final KafkaTemplate<UUID, UserCreatedEvent> kafkaTemplate;
    private final AuthAssembler authAssembler;
    private final JwtService jwtService;

    private static final String USER_CREATED_TOPIC = "user.created";

    @Override
    public AuthDTO register(RegisterPojo request) {

        log.info("Registering user: {}", request.getEmail());
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered: " + request.getEmail());
        }

        User user = authAssembler.assemble(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        log.info("User registered: {}", savedUser.getId());

        // user publish event
        UserCreatedEvent userCreatedEvent = new UserCreatedEvent(savedUser.getId(),
                savedUser.getFirstName(), savedUser.getLastName(), savedUser.getEmail(),
                savedUser.getHeadline(), savedUser.getLocation());


        kafkaTemplate.send(USER_CREATED_TOPIC, savedUser.getId(), userCreatedEvent);
        log.info("user.created event published {}", savedUser.getId());

        return getAuthDTO(savedUser);
    }

    @Override
    public AuthDTO login(LoginPojo request) {
        log.info("Login attempt: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found: " + request.getEmail()));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        log.info("Login successful: {}", user.getId());

        return getAuthDTO(user);
    }

    private AuthDTO getAuthDTO(User user) {
        AuthDTO authDTO = authAssembler.assembleDTO(user);

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getId());

        authDTO.setAccessToken(accessToken);
        authDTO.setRefreshToken(refreshToken);

        return authDTO;
    }


}
