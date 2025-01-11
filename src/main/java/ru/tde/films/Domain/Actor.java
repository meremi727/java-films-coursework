package ru.tde.films.Domain;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Actor extends Person {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String country;

    @ManyToMany
    private List<Film> films;

    /// Возвращает пол.
    public Gender getGender() { return gender; }

    /// Устанавливает новое значениеи пола.
    public void setGender(Gender gender) { this.gender = gender; }

    /// Возвращает страну.
    public String getCountry() { return country; }

    /// Устанавливает новое значение страны.
    public void setCountry(String country) { this.country = country; }

    /// Возвращает все фильмы, в которых снялся актер.
    public List<Film> getFilms() { return Collections.unmodifiableList(films); }
}
