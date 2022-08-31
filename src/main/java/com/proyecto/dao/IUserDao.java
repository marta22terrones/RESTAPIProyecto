package com.proyecto.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.entities.User;

@Repository
public interface IUserDao extends JpaRepository<User, Integer> {
    
}
