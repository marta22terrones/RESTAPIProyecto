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



/* Todas las peticiones se hacen a la URI "/productos" (end point), es decir, al recurso, y
 * en dependencia del verbo HTTP utilizado (GET, POST, PUT, PATCH, OPTIONS, etc)
 * se delegara a un metodo u otro.
 */

@RestController
@RequestMapping("/films")
public class MainController {
    
    @Autowired
    private IFilmService filmService;

    // El siguiente metodo tiene que responder a una request de tipo
    // http://localhost:8080/productos?page=1&size=3
    @GetMapping
    public ResponseEntity<List<Film>> findAll(@RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) {

        List<Film> films = null;
        ResponseEntity<List<Film>> responseEntity = null;

        // Ordenamos por el nombre del producto
        Sort sortByName = Sort.by("title");

        // Comprobamos si en la request me estan enviando page y size
        if (page != null && size != null) {


            // Con paginacion
            Pageable pageable = PageRequest.of(page, size, sortByName);

            films = filmService.findAll(pageable).getContent();

        } else {
            // Sin paginacion
            films = filmService.findAll(sortByName);
        }

        if (films.size() > 0) {
            responseEntity = new ResponseEntity<List<Film>>(films, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return responseEntity;
    }
}
