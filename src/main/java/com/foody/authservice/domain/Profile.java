package com.foody.authservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String bio;
}
