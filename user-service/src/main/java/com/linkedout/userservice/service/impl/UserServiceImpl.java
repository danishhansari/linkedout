package com.linkedout.userservice.service.impl;

import com.linkedout.userservice.assembler.UserAssembler;
import com.linkedout.userservice.dto.UserDTO;
import com.linkedout.userservice.entity.Connection;
import com.linkedout.userservice.enums.ConnectionStatus;
import com.linkedout.userservice.entity.User;
import com.linkedout.userservice.event.UserCreatedEvent;
import com.linkedout.userservice.event.UserUpdatedEvent;
import com.linkedout.userservice.repository.ConnectionRepository;
import com.linkedout.userservice.repository.UserRepository;
import com.linkedout.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ConnectionRepository connectionRepository;
    private final UserAssembler userAssembler;
    private final S3Service s3Service;

    private final static String USER_UPDATED_TOPIC = "user.updated";

    private final KafkaTemplate<UUID, Object> kafkaTemplate;

    private final static String CONNECTION_REQUESTED_TOPIC = "connection_requested";

    @Override
    public String sendConnectionRequest(String receiverId, String requestedId) {
        if (connectionRepository.existsByRequesterIdAndReceiverId(receiverId,  requestedId)) {
            throw new RuntimeException("Connection request already exists");
        }
        Connection connection = new Connection();
        connection.setRequestedId(requestedId);
        connection.setReceiverId(receiverId);
        connection.setConnectionStatus(ConnectionStatus.PENDING);
        connectionRepository.save(connection);

        // publish event for
        Map<String, Object> connectionRequestEvent = new HashMap<>();
        connectionRequestEvent.put("requesterId", requestedId);
        connectionRequestEvent.put("receiverId", receiverId);
        kafkaTemplate.send(CONNECTION_REQUESTED_TOPIC, requestedId, connectionRequestEvent);

        log.info("Connection request sent: {}, {}", requestedId, receiverId);
        return "Connection request sent";
    }

    @Override
    public UserDTO getUserProfile(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return userAssembler.assembleDTO(user);
    }

    @Override
    public UserDTO updateProfile(UUID userId, UserDTO userDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setHeadline(userDTO.getHeadline());
        user.setAbout(userDTO.getAbout());
        user.setLocation(userDTO.getLocation());
        user.setSkills(userDTO.getSkills());

        User savedUser = userRepository.save(user);

        UserUpdatedEvent userCreatedEvent = new UserUpdatedEvent(savedUser.getId(),
                savedUser.getFirstName(), savedUser.getLastName(), savedUser.getHeadline(),
                savedUser.getLocation(), savedUser.getSkills());


        kafkaTemplate.send(USER_UPDATED_TOPIC, savedUser.getId(), userCreatedEvent);

        log.info("user.updated event published {}", savedUser.getId() );
        return userAssembler.assembleDTO(savedUser);
    }

    @Override
    public UserDTO uploadProfilePhoto(UUID userId, MultipartFile file) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        String photoUrl = s3Service.uploadFile(file, "profiles/" + userId + "/avatar");
        user.setProfilePictureUrl(photoUrl);
        User savedUser = userRepository.save(user);
        log.info("user profile photo upload: {}", userId);
        return userAssembler.assembleDTO(savedUser);
    }
}
