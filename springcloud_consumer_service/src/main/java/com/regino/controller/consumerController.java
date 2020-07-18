package com.regino.controller;

import com.regino.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("consumer")
public class consumerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("findUserById/{id}")
    public User findUserById(@PathVariable("id") Integer id) {
        String url = "http://localhost:9091/user/findUserById/" + id;
        User user = restTemplate.getForObject(url, User.class);
        return user;
    }
}
