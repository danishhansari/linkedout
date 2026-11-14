package com.linkedout.userservice.service;

import com.linkedout.userservice.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {
    String sendConnectionRequest(String receiverId, String requestedId);
    UserDTO getUserProfile(UUID userId);
    UserDTO updateProfile(UUID userId, UserDTO userDTO);
    UserDTO uploadProfilePhoto(UUID userId, MultipartFile profilePhoto);
}
