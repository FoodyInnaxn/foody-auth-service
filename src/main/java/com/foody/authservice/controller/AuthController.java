package com.foody.authservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.foody.authservice.business.AuthService;
import com.foody.authservice.business.AuthServiceImpl;
import com.foody.authservice.domain.*;
import com.foody.authservice.domain.request.CreateUserRequest;
import com.foody.authservice.domain.request.LoginRequest;
import com.foody.authservice.domain.request.UpdateUserRequest;
import com.foody.authservice.domain.response.CreateUserResponse;
import com.foody.authservice.domain.response.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthServiceImpl authService){this.authService = authService; }

    @PostMapping("/foody/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest authRequest) throws AuthenticationException {
        return ResponseEntity.ok(this.authService.loginUser(authRequest));
    }

    @PostMapping("/foody/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse addNewUser(@RequestBody @Valid CreateUserRequest request) throws JsonProcessingException {
        return this.authService.createUser(request);
    }

    @GetMapping("/foody/profile/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Profile getProfile(@PathVariable long id){
        return this.authService.getUser(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        request.setId(id);
        authService.updateUser(request);
        return ResponseEntity.noContent().build();
    }

}
