package com.foody.authservice.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.foody.authservice.business.exception.UserNotFound;
import com.foody.authservice.business.rabbit.AuthEventPublisher;
import com.foody.authservice.business.rabbit.UserToSend;
import com.foody.authservice.business.validation.EmailValidator;
import com.foody.authservice.business.validation.UsernameValidator;
import com.foody.authservice.configuration.RabbitMQConfig;
import com.foody.authservice.domain.*;
import com.foody.authservice.domain.request.CreateUserRequest;
import com.foody.authservice.domain.request.LoginRequest;
import com.foody.authservice.domain.request.UpdateUserRequest;
import com.foody.authservice.domain.response.CreateUserResponse;
import com.foody.authservice.domain.response.LoginResponse;
import com.foody.authservice.persistence.UserCredentialsRepository;
import com.foody.authservice.persistence.entity.UserCredential;
import com.foody.authservice.persistence.entity.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private PasswordEncoder passwordEncoder;
    private AuthEventPublisher authEventPublisher;
    private final UserCredentialsRepository userCredentialsRepository;
    private final AccessTokenEncoder accessTokenEncoder;
    private EmailValidator emailValidator;
    private UsernameValidator usernameValidator;
    private final ServiceClient serviceClient;

    @Override
    @RabbitListener(queues = RabbitMQConfig.AUTH_DELETE_QUEUE)
    public void deleteUser(Long userId){
        this.userCredentialsRepository.deleteById(userId);
    }

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) throws JsonProcessingException {
        emailValidator.validateEmail(request.getEmail(), null);
        usernameValidator.validateUsername(request.getUsername(), null);
        UserCredential savedNewUser = saveNewUser(request);
        //rabbit sends to user
        authEventPublisher.publishCreatedAuthEvent(new UserToSend(request.getFirstName(), request.getLastName(), request.getBio(), savedNewUser.getId()));
        return new CreateUserResponse(savedNewUser.getId());
    }

    private UserCredential saveNewUser(CreateUserRequest request){
        UserCredential userCredential = new
                UserCredential(request.getUsername(), request.getEmail(),
                passwordEncoder.encode(request.getPassword()), UserRole.MODERATOR);

        return this.userCredentialsRepository.save(userCredential);
    }

    @Override
    public Profile getUser(Long userId){
        UserInfo userInfo = this.serviceClient.getUser(userId);
        UserCredential userCredential = userCredentialsRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        User user = new User(userCredential.getUsername(), userCredential.getEmail());

        return new Profile(userId, user.getUsername(), user.getEmail(), userInfo.getFirstName(), userInfo.getLastName(),
                userInfo.getBio());

    }
    @Override
    public void updateUser(UpdateUserRequest request){
        UserCredential userCredentialEntity = this.userCredentialsRepository.findById(request.getId())
                .orElseThrow(UserNotFound::new);

        if(!userCredentialEntity.getEmail().matches(request.getEmail())){
            emailValidator.validateEmail(request.getEmail(), request.getId());
        }

        if(!userCredentialEntity.getUsername().matches(request.getUsername())){
            usernameValidator.validateUsername(request.getUsername(), request.getId());
        }

        userCredentialEntity.setEmail(request.getEmail());
        userCredentialEntity.setUsername(request.getUsername());

        this.userCredentialsRepository.save(userCredentialEntity);
    }
    
    @Override
    public LoginResponse loginUser(LoginRequest request) throws AuthenticationException {
        UserCredential userCredentialEntity = this.userCredentialsRepository.findByEmail(request.getEmail()).orElseThrow();

        if(!passwordEncoder.matches(request.getPassword(), userCredentialEntity.getPassword())){
            throw new AuthenticationException();
        }

        String accessToken = generateAccessToken(userCredentialEntity);

        return new LoginResponse(accessToken);
    }

    private String generateAccessToken(UserCredential userCredentialEntity){
        Long userId = userCredentialEntity.getId();
        String role = userCredentialEntity.getRole().toString();

        return accessTokenEncoder.encode(
                new AccessToken(userCredentialEntity.getUsername(), role, userId)
        );
    }
}
