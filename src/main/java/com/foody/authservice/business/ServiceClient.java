package com.foody.authservice.business;

import com.foody.authservice.controller.FeignUtil;
import com.foody.authservice.domain.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceClient {
    @Autowired
    private final FeignUtil feignUtil;

    public UserInfo getUser(Long authId){
        return feignUtil.getUser(authId);
    }

}
