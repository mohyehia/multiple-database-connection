package com.mohyehia.multipledatabaseconnection.user.service.implementation;

import com.mohyehia.multipledatabaseconnection.user.dao.UserDAO;
import com.mohyehia.multipledatabaseconnection.user.entity.User;
import com.mohyehia.multipledatabaseconnection.user.service.framework.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email).orElse(null);
    }

    @Override
    public User save(User user) {
        return userDAO.save(user);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }
}
