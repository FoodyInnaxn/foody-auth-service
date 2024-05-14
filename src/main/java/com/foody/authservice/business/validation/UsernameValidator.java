package com.foody.authservice.business.validation;

import com.foody.authservice.business.exception.InvalidEmailException;
import com.foody.authservice.business.exception.InvalidUsernameException;
import com.foody.authservice.persistence.UserCredentialsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsernameValidator {
    private final UserCredentialsRepository userCredentialsRepository;

    public void validateUsername(String username, Long userId) throws InvalidUsernameException {

        if(userId != null){
            if(userCredentialsRepository.existsByUsernameExcludingUserId(username, userId)){
                throw new InvalidEmailException();
            }
        }
    }
}
