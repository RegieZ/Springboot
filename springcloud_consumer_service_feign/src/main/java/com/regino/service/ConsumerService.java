package com.regino.service;

import com.regino.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="springcloud-user-service")
public interface ConsumerService {

    /*
        String url = "http://springcloud-user-service/user/findUserById/
     */
    @GetMapping("/user/findUserById/{id}")
    User findUserById(@PathVariable("id") Integer id);
}
