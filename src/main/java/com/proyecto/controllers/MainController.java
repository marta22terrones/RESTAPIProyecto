package com.proyecto.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.entities.Film;
import com.proyecto.service.IFilmService;


@RestController
@RequestMapping("/films")
public class MainController {

    @Autowired
    private IFilmService filmService;

    @GetMapping
    public ResponseEntity<List<Film>> findAll(@RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) {

        List<Film> films = null;
        ResponseEntity<List<Film>> responseEntity = null;

        Sort sortById = Sort.by("id");

        if (page != null && size != null) {

            Pageable pageable = PageRequest.of(page, size, sortById);

            films = filmService.findAll(pageable).getContent();

        } else {
            films = filmService.findAll(sortById);
        }

        if (films.size() > 0) {
            responseEntity = new ResponseEntity<List<Film>>(films, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> findById(@PathVariable(name = "id") Integer id) {

        Film film = filmService.getFilm(id);

        if (film != null)
            return new ResponseEntity<Film>(film, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> insert(@Valid @RequestBody Film film, BindingResult result) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        List<String> errorMessages = new ArrayList<>();

        if (result.hasErrors()) {

            List<ObjectError> errors = result.getAllErrors();

            for (ObjectError error : errors) {

                errorMessages.add(error.getDefaultMessage());
            }

            responseAsMap.put("errors", errorMessages);
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }

        try {
            Film filmDB = filmService.saveFilm(film);

            if (filmDB != null) {
                responseAsMap.put("film", filmDB);
                responseAsMap.put("message",
                        "The film with id " + filmDB.getId() + ", has been created correctly.");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
            } else {
                responseAsMap.put("message", "The film hasn't been created");
                responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (DataAccessException e) {
            responseAsMap.put("message",
                    "There has been a serious error during the creation of the film, and the most likely cause is: " + e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable(name = "id") int id,
            @Valid @RequestBody Film film, BindingResult result) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        List<String> errorMessages = new ArrayList<>();

        if (result.hasErrors()) {

            List<ObjectError> errors = result.getAllErrors();

            for (ObjectError error : errors) {

                errorMessages.add(error.getDefaultMessage());
            }

            responseAsMap.put("errors", errorMessages);
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }

        try {
            film.setId(id);
            Film filmDB = filmService.saveFilm(film);

            if (filmDB != null) {
                responseAsMap.put("film", filmDB);
                responseAsMap.put("message",
                        "The film with id " + filmDB.getId() + ", has been updated correctly.");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
            } else {
                responseAsMap.put("message", "It wasn't possible to update the film");
                responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (DataAccessException e) {
            responseAsMap.put("message", "There has been a serious error in the update of the film, and the most likely cause is: "
                    + e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable(name = "id") int id) {

        Film film = filmService.getFilm(id);

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        if (film != null) {

            try {
                filmService.delete(id);
                responseAsMap.put("message", "This film has been deleted correctly: " + film.getId());
                responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
            } catch (DataAccessException e) {
                e.getMostSpecificCause();
            }

        } else {
            responseAsMap.put("message", "It wasn't possible to delete the film");
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
