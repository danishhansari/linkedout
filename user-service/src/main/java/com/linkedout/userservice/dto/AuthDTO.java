package com.linkedout.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {

    private UUID userId;

    private String firstName;
    private String lastName;
    private String email;

    private String refreshToken;
    private String accessToken;
    private String tokenType = "Bearer";
}
