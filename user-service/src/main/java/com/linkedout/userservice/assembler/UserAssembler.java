package com.linkedout.userservice.assembler;

import com.linkedout.userservice.dto.UserDTO;
import com.linkedout.userservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {
    public UserDTO assembleDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setHeadline(user.getHeadline());
        userDTO.setAbout(user.getAbout());
        userDTO.setLocation(user.getLocation());
        userDTO.setProfilePictureUrl(user.getProfilePictureUrl());
        userDTO.setCoverPictureUrl(user.getCoverPictureUrl());
        userDTO.setRole(user.getRole());
        userDTO.setSkills(user.getSkills());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }
}
