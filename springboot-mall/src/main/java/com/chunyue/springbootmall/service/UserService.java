package com.chunyue.springbootmall.service;

import com.chunyue.springbootmall.dto.UserRegisterRequest;
import com.chunyue.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);
}
