package com.linkedout.userservice.assembler;

import com.linkedout.userservice.dto.AuthDTO;
import com.linkedout.userservice.entity.User;
import com.linkedout.userservice.entity.UserRole;
import com.linkedout.userservice.pojo.RegisterPojo;
import org.springframework.stereotype.Component;

@Component
public class AuthAssembler {
    public User assemble(RegisterPojo request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setHeadline(request.getHeadline());
        user.setAbout(request.getAbout());
        user.setLocation(request.getLocation());
        user.setRole(UserRole.USER);
        return user;
    }

    public AuthDTO assembleDTO(User user) {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUserId(user.getId());
        authDTO.setFirstName(user.getFirstName());
        authDTO.setLastName(user.getLastName());
        authDTO.setEmail(user.getEmail());
        return authDTO;
    }
}