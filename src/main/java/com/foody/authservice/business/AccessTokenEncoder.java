package com.foody.authservice.business;

import com.foody.authservice.domain.AccessToken;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
