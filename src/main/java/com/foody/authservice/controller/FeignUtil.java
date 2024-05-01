package com.foody.authservice.controller;

import com.foody.authservice.domain.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service", url="http://localhost:8082")
public interface FeignUtil {

    @GetMapping("/user/profile/{id}")
    UserInfo getUser(@PathVariable("id") Long id);
}
