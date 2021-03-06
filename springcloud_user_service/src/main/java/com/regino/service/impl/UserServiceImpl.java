package com.regino.service.impl;

import com.regino.dao.UserDao;
import com.regino.pojo.User;
import com.regino.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findUserById(Integer id) {
        return userDao.findUserById(id);
    }
}
