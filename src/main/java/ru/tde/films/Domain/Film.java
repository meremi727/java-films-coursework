package ru.tde.films.Domain;

import java.time.Duration;
import java.util.*;
import jakarta.persistence.*;

@Entity
@Table(name = "Film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private Date dateReleased;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Duration timeDuration;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Director> directors;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Actor> actors;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "film", cascade = CascadeType.MERGE)
    private List<Comment> comments;

    /// Возвращает уникальный идентификатор фильма.
    public int getId() { return id; }

    /// Возвращает название фильма.
    public String getTitle() { return title; }

    /// Устанавливает название фильма.
    public void setTitle(String title) { this.title = title; }

    /// Возвращает дату выпуска фильма.
    public Date getDateReleased() { return dateReleased; }

    /// Устанавливает дату выпуска фильма.
    public void setDateReleased(Date dateReleased) { this.dateReleased = dateReleased; }

    /// Возвращает жанр фильма.
    public String getGenre() { return genre; }

    /// Устанавливает жанр фильма.
    public void setGenre(String genre) { this.genre = genre; }

    /// Возвращает страну выпуска фильма.
    public String getCountry() { return country; }

    /// Устанавливает страну выпуска фильма.
    public void setCountry(String country) { this.country = country; }

    /// Возвращает описание фильма.
    public String getDescription() { return description; }

    /// Устанавливает описание фильма.
    public void setDescription(String description) { this.description = description; }

    /// Возвращает продолжительность фильма.
    public Duration getTimeDuration() { return timeDuration; }

    /// Устанавливает продолжительность фильма.
    public void setTimeDuration(Duration timeDuration) { this.timeDuration = timeDuration; }

    /// Возвращает список всех режиссеров фильма.
    public List<Director> getDirectors() { return directors; }

    public void setDirectors(List<Director> directors) { this.directors = directors; }

    /// Возвращает список всех актеров фильма.
    public List<Actor> getActors() { return actors; }

    public void setActors(List<Actor> actors) { this.actors = actors; }

    /// Возвращает список всех отзывов фильма.
    public List<Comment> getComments() { return comments; }

    public void setComments(List<Comment> comments) { this.comments = comments; }
}
