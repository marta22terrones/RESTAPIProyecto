package com.proyecto.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "The username cannot be empty")
    @Size(min = 4, max = 20, message = "The username must have between 4 and 20 characters")
    private String username;

    @NotEmpty(message = "The password cannot be empty")
    @Size(min = 4, max = 20, message = "The password must have between 4 and 20 characters")
    private String password;

    @NotEmpty(message = "The date of birth cannot be empty")
    private LocalDate dateBirth;

    private Genre genre;

    private Rol rol;
    
    private String avatar;

    // @ManyToMany(mappedBy = "ratings")
    // private List<Integer> ratings = new ArrayList<Integer>();

    public enum Genre {
        MAN, WOMAN, OTHER
    }

    public enum Rol {
        ADMIN, NORMAL
    }
}
