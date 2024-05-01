package com.foody.authservice.business;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.foody.authservice.domain.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class AccessTokenEncoderImpl implements AccessTokenEncoder {
    private final Algorithm key;

    public AccessTokenEncoderImpl(@Value("${jwt.secret}") String key){this.key = Algorithm.HMAC256(key);}

    @Override
    public String encode(AccessToken accessToken){
        Instant now = Instant.now();

        return JWT.create().withClaim("role", accessToken.getRole())
                .withClaim("userId", accessToken.getUserId())
                .withIssuedAt(now)
                .withExpiresAt(Date.from(now.plus(45, ChronoUnit.DAYS)))
                .sign(key);
    }

}
