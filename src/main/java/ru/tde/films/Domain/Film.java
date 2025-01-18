package ru.tde.films.Domain;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;
import ru.tde.films.Views.Util.Annotation.Translation;


@Entity
@Data
public class Film {

    public Film() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Translation("Название")
    private String title;

    @Translation("Дата выхода")
    private Date dateReleased;

    @Translation("Жанр")
    private String genre;

    @Translation("Страна")
    private String country;

    @Translation("Описание")
    private String description;

    @Translation("Продолжительность")
    private Integer timeDuration;

    @Translation("Режиссеры")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Director> directors = new HashSet<>();

    @Translation("Актеры")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Actor> actors = new HashSet<>();

    @Translation("Комментарии")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();
}
