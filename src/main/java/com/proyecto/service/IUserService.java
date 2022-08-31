package com.proyecto.service;

import java.util.List;

import com.proyecto.entities.User;

/*
 * Prototipo IUserService: opción de aumentar métodos
 */
public interface IUserService {
    
    public User getUser(int id);
    public List<User> getUsers();
    public void saveUser (User user);
    public void deleteUser (int id);

}
