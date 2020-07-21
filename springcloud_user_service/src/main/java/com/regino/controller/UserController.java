package com.regino.controller;

import com.regino.pojo.User;
import com.regino.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${server.port}")
    private String port;


    // restful风格：
    // @RequestParam
    // @PathVariable("id")
    // @PathVariable
    @GetMapping("findUserById/{id}")
    public User findUserbyId(@PathVariable("id") Integer id) {
        User user = userService.findUserById(id);
        user.setNote("生产者端口号是: " + port);
        return user;
    }
}
