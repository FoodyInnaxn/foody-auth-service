package com.foody.authservice.business.validation;
import com.foody.authservice.business.exception.InvalidEmailException;
import com.foody.authservice.persistence.UserCredentialsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class EmailValidator {
    private final UserCredentialsRepository userCredentialsRepository;

    public void validateEmail(String email, Long userId) throws InvalidEmailException {

        if(userId != null){
            if(userCredentialsRepository.existsByEmailAndId(email, userId)){
                throw new InvalidEmailException();
            }
        }

        if(userCredentialsRepository.existsByEmail(email)){
            throw new InvalidEmailException();
        }

    }
}