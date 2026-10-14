package com.linkedout.userservice.service;

import com.linkedout.userservice.dto.AuthDTO;
import com.linkedout.userservice.pojo.LoginPojo;
import com.linkedout.userservice.pojo.RegisterPojo;

public interface AuthService {
    AuthDTO register(RegisterPojo request);
    AuthDTO login(LoginPojo request);
}
