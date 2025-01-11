package ru.tde.films.Domain;

import java.util.*;
import jakarta.persistence.*;

@Entity
public class Director extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany()
    private List<Film> films;

    /// Возвращает все фильмы данного режиссера.
    public List<Film> getFilms() { return Collections.unmodifiableList(films); }
}
