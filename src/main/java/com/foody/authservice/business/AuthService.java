package com.foody.authservice.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.foody.authservice.domain.Profile;
import com.foody.authservice.domain.request.CreateUserRequest;
import com.foody.authservice.domain.request.LoginRequest;
import com.foody.authservice.domain.request.UpdateUserRequest;
import com.foody.authservice.domain.response.CreateUserResponse;
import com.foody.authservice.domain.response.LoginResponse;

import javax.naming.AuthenticationException;


public interface AuthService {
    void deleteUser(Long userId);
    CreateUserResponse createUser(CreateUserRequest request)  throws JsonProcessingException;
    Profile getUser(Long userId);
    void updateUser(UpdateUserRequest request);
    LoginResponse loginUser(LoginRequest request) throws AuthenticationException;
}
