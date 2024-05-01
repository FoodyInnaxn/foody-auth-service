package com.foody.authservice.business.rabbit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserToSend {
    private String firstName;
    private String lastName;
    private String bio;
    private  Long authId;
}
