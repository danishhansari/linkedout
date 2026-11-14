package com.linkedout.userservice.controller;

import com.linkedout.userservice.dto.UserDTO;
import com.linkedout.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    /*
    * Get user Profile
    * X-user-id = Requesting from (gateway)
    * userId in path variable for the target user
    * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable UUID userId,
                                           @RequestHeader("X-User-Id") String requestingUserId) {

        log.info("Getting user profile for {} and requested by {}", userId, requestingUserId);
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    /*
     * Update user Profile
     * X-user-id = Requesting from (gateway)
     * userId in path variable for the target user
     * @return
     */
    @PutMapping("/{userId}/profile")
     public ResponseEntity<UserDTO> updateProfile(@PathVariable UUID userId,
                                                  @RequestHeader("X-User-Id") String requestingUserId,
                                                  @RequestBody UserDTO userDTO) {
        if(!userId.equals(requestingUserId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(userService.updateProfile(userId, userDTO));
    }

    /*
     * Update user Profile
     * X-user-id = Requesting from (gateway)
     * userId in path variable for the target user
     * @return
     */
    @PostMapping("/{userId}/profile-photo")
    public ResponseEntity<UserDTO> uploadProfilePhoto(@PathVariable UUID userId,
                                                 @RequestHeader("X-User-Id") String requestingUserId,
                                                 @RequestParam("file") MultipartFile file) {
        if(!userId.equals(requestingUserId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(userService.uploadProfilePhoto(userId, file));
    }


    /*
     * send connection to other user
     * X-user-id = Requesting from (gateway)
     * userId in path variable for the target user
     * @return
     */
    @PostMapping("/{targetUserId}/profile")
    public ResponseEntity<String> sendConnectionRequest(@PathVariable String targetUserId,
                                                        @RequestHeader("X-User-Id") String requestingUserId) {
        return ResponseEntity.ok(userService.sendConnectionRequest(targetUserId, requestingUserId));
    }

    /*
     * Accepting connection to other user
     * X-user-id = Requesting from (gateway)
     * connection Id in path variable for the connection request
     * @return
     */
    @PutMapping("/connection/{connectionId}/accept")
    public ResponseEntity<String> acceptConnection(@PathVariable UUID connectionId,
                                                        @RequestHeader("X-User-Id") String requestingUserId) {
        return ResponseEntity.ok(userService.acceptConnectionRequest(connectionId));
    }

    /*
     * List of connection request received
     * X-user-id = Requesting from (gateway)
     * userId in path variable for the target user
     * @return
     */
    @GetMapping("/{userId}/connections")
    public ResponseEntity<List<UserDTO>> getConnection(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getConnections(userId));
    }

}
