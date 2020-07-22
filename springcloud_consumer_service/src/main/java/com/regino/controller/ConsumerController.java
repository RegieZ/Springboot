package com.regino.controller;

import com.regino.pojo.User;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("consumer")
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("findUserById/{id}")
    @HystrixCommand(fallbackMethod = "fallbackMethod") //声明默认失败方法
    public User findUserById(@PathVariable("id") Integer id) {

        /*
            1.url硬编码(写死了) --> springcloud euraka（注册中心）
            2.负载均衡问题 --> springcloud ribbon（负载均衡器）
            3.无法感知调用服务的状态 --> springcloud hystrix（熔断器）
         */

        /*String url = "http://localhost:9091/user/findUserById/" + id;*/

        /*List<ServiceInstance> instances = discoveryClient.getInstances("SPRINGCLOUD_USER_SERVICE");
        URI uri = instances.get(0).getUri();
        String url = uri + "/user/findUserById/" + id;*/

        String url = "http://springcloud_user_service/user/findUserById/" + id;

        User user = restTemplate.getForObject(url, User.class);
        return user;
    }

    /*
        默认熔断方法：
            1.返回值类型与原方法一致
            2.参数列表必须与原方法一致
            3.方法名称必须是fallbackMethod中声明的方法名
     */
    public User fallbackMethod(Integer id){
        User user = new User();
        user.setId(id);
        user.setNote("默认熔断方法");
        return user;
    }

    /*
        返回服务列表
     */
    @GetMapping("/getInstance")
    public List<ServiceInstance> getInstance() {
        List<ServiceInstance> instances = discoveryClient.getInstances("SPRINGCLOUD_USER_SERVICE");
        return instances;
    }
}
