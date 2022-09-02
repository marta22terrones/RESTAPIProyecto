package com.proyecto.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proyecto.entities.Film;

@Repository
public interface IFilmDao extends JpaRepository<Film, Integer> {

    @Query(value = "select f from Film f where f.title like %:string%")
    public List<Film> getFilmByString(String string);

        // // Recupera un listado de productos ordenado
        // @Query(value = "select f from Film f left join fetch f.genre")
        // public List<Film> findAll(Sort sort);
    
        // // Recupera un listado de productos por Pagina, o mejor dicho:
        // // Recuperamos un listado de productos que se puede paginar (Pajeable de Producto)
        // @Query(value = "select f from Film f left join fetch f.genre", 
        //         countQuery = "select count(f) from Film f left join f.genre")
        // public Page<Film> findAll(Pageable pageable);
    
        // // Recuperar un producto por el ID
        // // La consulta siguiente utiliza una sintaxis que se llama Named Parameters,
        // // que es muy similar a una sentencia preparada (prepared statement) que en lugar 
        // // del caracter ? utiliza los dos puntos para hacer referencia al nombre del parametro que hay
        // // que sustituir en la query
        // @Query(value = "select p from Producto p left join fetch p.presentacion where p.id = :id")
        // public Film findById(long id);
}
