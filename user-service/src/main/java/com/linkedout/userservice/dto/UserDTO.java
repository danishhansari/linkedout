package com.linkedout.userservice.dto;

import com.linkedout.userservice.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;

    private String email;
    private String firstName;
    private String lastName;
    private String headline;
    private String about;
    private String location;

    private String profilePictureUrl;
    private String coverPictureUrl;

    private UserRole role;
    private List<String> skills;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
