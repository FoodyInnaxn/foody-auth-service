package com.foody.authservice.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="auth")
public class UserCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "username", unique = true)
    private String username;

    @Column(name= "email",unique = true)
    private String email;

    @Column(name= "password", unique = true)
    @Size(max = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserCredential(String username, String email, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
