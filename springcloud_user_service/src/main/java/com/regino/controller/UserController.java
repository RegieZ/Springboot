package com.regino.controller;

import com.regino.pojo.User;
import com.regino.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RefreshScope //开启刷新
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${server.port}")
    private String port;

    @Value("${personName}")
    private String personName;

    // restful风格：
    // @RequestParam
    // @PathVariable("id")
    // @PathVariable
    @GetMapping("findUserById/{id}")
    public User findUserbyId(@PathVariable("id") Integer id) {
        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        User user = userService.findUserById(id);
        user.setNote("生产者端口号是: " + port);
        return user;
    }

    /*
        用于区分配置文件
     */
    @GetMapping("/getPersonName")
    public String getPersonName(){
        return personName;
    }
}
