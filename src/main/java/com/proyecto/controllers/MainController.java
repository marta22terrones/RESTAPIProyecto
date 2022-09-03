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
        Sort sortById = Sort.by("id");

        // Comprobamos si en la request me estan enviando page y size
        if (page != null && size != null) {

            // Con paginacion
            Pageable pageable = PageRequest.of(page, size, sortById);

            films = filmService.findAll(pageable).getContent();

        } else {
            // Sin paginacion
            films = filmService.findAll(sortById);
        }

        if (films.size() > 0) {
            responseEntity = new ResponseEntity<List<Film>>(films, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return responseEntity;
    }

    // El siguiente metodo recupera un producto dado el ID que recibe como parametro
    // http://localhost:8080/productos/3 (el 3 es el id del producto)
    @GetMapping("/{id}")
    public ResponseEntity<Film> findById(@PathVariable(name = "id") Integer id) {

        Film film = filmService.getFilm(id);

        if (film != null)
            return new ResponseEntity<Film>(film, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Guardar (Persistir), un producto, con su presentacion en la base de datos
    @PostMapping
    public ResponseEntity<Map<String, Object>> insert(@Valid @RequestBody Film film, BindingResult result) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        List<String> errorMessages = new ArrayList<>();

        // Primero: Comprobar si hay errores en el producto recibido
        if (result.hasErrors()) {

            // Recuperamos la lista de errores
            List<ObjectError> errores = result.getAllErrors();

            // Recorrer la lista de errores
            for (ObjectError error : errores) {

                // Añadimos los mensajes de error, que estan en la entidad, Producto en este
                // caso
                // a una lista
                errorMessages.add(error.getDefaultMessage());
            }

            responseAsMap.put("errores", errorMessages);
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }

        // Si no hay errores podemos persistir el producto en la base de datos
        try {
            Film filmDB = filmService.saveFilm(film);

            if (filmDB != null) {
                // Se ha guardado el producto correctamente
                responseAsMap.put("film", filmDB);
                responseAsMap.put("mensaje",
                        "The film with id " + filmDB.getId() + ", has been created correctly!!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
            } else {
                // No se ha guardado correctamente el producto
                responseAsMap.put("mensaje", "No se ha podido crear el film");
                responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (DataAccessException e) {
            responseAsMap.put("mensaje",
                    "Error grave en la creacion del producto, y la causa mas probable es: " + e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }


}
