package com.proyecto.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proyecto.entities.Film;

@Repository
public interface IFilmDao extends JpaRepository<Film, Integer> {

    @Query(value = "select f from Film f where f.title like %:string%")
    public List<Film> getFilmByString(String string);
}
