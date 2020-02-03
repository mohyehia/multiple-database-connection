package com.mohyehia.multipledatabaseconnection.user.service.framework;

import com.mohyehia.multipledatabaseconnection.user.entity.User;

public interface UserService {
    User findByEmail(String email);
}
