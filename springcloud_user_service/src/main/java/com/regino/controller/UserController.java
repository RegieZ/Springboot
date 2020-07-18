package com.regino.controller;

import com.regino.pojo.User;
import com.regino.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // restful风格：
    // @RequestParam
    // @PathVariable("id")
    // @PathVariable
    @GetMapping("findUserById/{id}")
    public User findUserbyId(@PathVariable("id") Integer id) {
        return userService.findUserById(id);
    }
}
