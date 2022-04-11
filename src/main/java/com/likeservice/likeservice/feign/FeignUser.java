package com.likeservice.likeservice.feign;


import com.likeservice.likeservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service")
public interface FeignUser {
    @GetMapping("/users/{userId}")
    public User findByID(@PathVariable("userId") String userId);
}
