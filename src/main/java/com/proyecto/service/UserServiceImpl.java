package com.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.dao.IUserDao;
import com.proyecto.entities.User;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    public IUserDao userDao;

    @Override
    public User getUser(int id) {
        return userDao.findById(id).get();
    }

    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    public void saveUser(User user) {
        userDao.save(user);
        
    }

    @Override
    public void deleteUser(int id) {
        userDao.deleteById(id);
        
    }
    
}
