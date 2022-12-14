package com.proyecto.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.proyecto.entities.Actor;
import com.proyecto.entities.Director;
import com.proyecto.entities.Film;
import com.proyecto.entities.Genre;

public interface IFilmService {
    public List<Film> getFilms();
    public Film getFilm(int filmId);
    public List<Genre> getGendres(int filmId);
    public List<Actor> getActors(int filmId);
    public List<Director> getDirectors(int filmId);
    public void save(Film film);
    public void delete(int filmId);
    public List<Film> getFilmByString(String string);
    public List<Film> findAll(Sort sort);
    public Page<Film> findAll(Pageable pageable);
    public Film saveFilm(Film film);
}
