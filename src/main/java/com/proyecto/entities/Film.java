package com.proyecto.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Film implements Serializable {

    private static final long SerialVersionUID = 1L; //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "The title cannot be empty")
    @Size(min = 2, max = 50, message = "The title must have between 2 and 50 characters")
    private String title;

    // @NotEmpty(message = "The year cannot be empty")
    @Range(min = 1, max = 2022, message = "Please input a valid year")
    // @Size(min = 4, max = 4, message = "Please input a valid year")
    private int year;

    // @NotEmpty(message = "The plot cannot be empty")
    @Size(min = 20, max = 500, message = "The plot must have between 20 and 500 characters")
    private String plot;

    private String poster;

    @Range(min = 1, max = 300, message = "Please input a valid length in minutes")
    // @Size(min = 2, max = 5, message = "Please input a valid length in minutes")
    private int length;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "film_genre", joinColumns = @JoinColumn(name = "film_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @NotNull(message = "The movie must have at least one genre")
    @ElementCollection(targetClass = Genre.class)
    private List<Genre> genres = new ArrayList<Genre>();

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "film_director", joinColumns = @JoinColumn(name = "film_id"), inverseJoinColumns = @JoinColumn(name = "director_id"))
    @NotNull(message = "The movie must have at least one director")
    @ElementCollection(targetClass = Director.class)
    private List<Director> directors = new ArrayList<Director>();

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "film_actor", joinColumns = @JoinColumn(name = "film_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
    @NotNull(message = "The movie must have at least one actor")
    @ElementCollection(targetClass = Actor.class)
    private List<Actor> actors = new ArrayList<Actor>();

    // @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    // @JoinTable(name = "user_ratings", joinColumns = @JoinColumn(name = "film_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    // private List<Integer> ratings = new ArrayList<Integer>();
}
