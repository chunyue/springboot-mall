package com.chunyue.springbootmall.service.impl;

import com.chunyue.springbootmall.dao.UserDao;
import com.chunyue.springbootmall.dto.UserRegisterRequest;
import com.chunyue.springbootmall.model.User;
import com.chunyue.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }
}
