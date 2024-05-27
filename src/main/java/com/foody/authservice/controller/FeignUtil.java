package com.foody.authservice.controller;

import com.foody.authservice.domain.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface FeignUtil {
//    url = "http://user-service-1:8082"
    @GetMapping("/user/profile/{id}")
    UserInfo getUser(@PathVariable("id") Long id);
}
