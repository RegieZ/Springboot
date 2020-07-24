package com.regino.service.impl;

import com.regino.pojo.User;
import com.regino.service.ConsumerService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Service or @Component
@Component
public class ConsumerServiceImpl implements ConsumerService {

    // 熔断方法
    @Override
    public User findUserById(Integer id) {
        User user = new User();
        user.setId(id);
        user.setNote("默认熔断方法");
        return user;
    }
}
