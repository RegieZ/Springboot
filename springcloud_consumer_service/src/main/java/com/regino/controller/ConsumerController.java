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
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @Date: 11:48 2020/7/17
     * @Param: [id]
     * @return: com.itheima.pojo.User
     * @NewDescription: 根据id查询用户
     **/
    @GetMapping("findUserById/{id}")
    public User findUserById(@PathVariable("id")Integer id){

        /*
            1.url硬编码(写死了) --> springcloud euraka（注册中心）
            2.负载均衡问题 --> springcloud ribbon（负载均衡器）
            3.无法感知调用服务的状态 --> springcloud hystrix（熔断器）
         */
        String url = "http://localhost:9091/user/findUserById/" + id;
        User user = restTemplate.getForObject(url, User.class);
        return user;
    }
}
