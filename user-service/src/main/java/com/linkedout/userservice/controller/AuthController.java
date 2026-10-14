package com.linkedout.userservice.controller;

import com.linkedout.userservice.dto.AuthDTO;
import com.linkedout.userservice.pojo.LoginPojo;
import com.linkedout.userservice.pojo.RegisterPojo;
import com.linkedout.userservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /*
    **    Register User
    */
    @PostMapping("/register")
    public ResponseEntity<AuthDTO> register(@Valid @RequestBody RegisterPojo request) {

        log.info("Register request: {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    /*
     **    Login User
     */
    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@Valid @RequestBody LoginPojo request) {
        log.info("Login request: {}", request.getEmail());

        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.login(request));
    }
}
