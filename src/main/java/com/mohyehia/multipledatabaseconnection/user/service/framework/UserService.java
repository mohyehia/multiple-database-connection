package com.mohyehia.multipledatabaseconnection.user.service.framework;

import com.mohyehia.multipledatabaseconnection.user.entity.User;

import java.util.List;

public interface UserService {
    User findByEmail(String email);
    User save(User user);
    List<User> findAll();
}
