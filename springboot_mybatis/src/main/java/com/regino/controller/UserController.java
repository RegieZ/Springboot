package com.regino.controller;

import com.alibaba.fastjson.JSON;
import com.regino.pojo.User;
import com.regino.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("findAll")
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/getRedis")
    public String getRedis(){
        //设值
        redisTemplate.boundValueOps("springboot").set("regino");
        //获取
        String str = (String) redisTemplate.boundValueOps("springboot").get();
        return str;
    }

    // 使用redis做缓存
    @GetMapping("findAllStr")
    public String findAllStr(){

        String userListStr = "";
        //1.从redis中获取userList
        userListStr = (String) redisTemplate.boundValueOps("springboot2").get();
        //2.获取到直接返回
        if(StringUtils.isEmpty(userListStr)){
            System.out.println("缓存中没有，从数据库中查询");
            //3.获取不到从数据库中查询
            List<User> userList = userService.findAll();
            //4.将结果序列化
            userListStr = JSON.toJSONString(userList);
            //5.将userList json 存放到redis中
            redisTemplate.boundValueOps("springboot2").set(userListStr);
        }
        return userListStr;
    }
}
