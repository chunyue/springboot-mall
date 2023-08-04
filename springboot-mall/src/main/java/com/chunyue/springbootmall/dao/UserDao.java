package com.chunyue.springbootmall.dao;

import com.chunyue.springbootmall.dto.UserRegisterRequest;
import com.chunyue.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
