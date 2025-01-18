package ru.tde.films.Domain;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;
import ru.tde.films.Views.Util.Annotation.Translation;


@Entity
@Data
public class Director  {

    public Director() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Translation("Пол")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Translation("Стана")
    private String country;

    @Translation("Фамилия")
    private String surname;

    @Translation("Имя")
    private String name;

    @Translation("Отчество")
    private String patronymic;

    @Translation("Дата рождения")
    private Date dateOfBirth;

    @Translation("Титул")
    private String dignity;

    @Translation("Фильмы")
    @ManyToMany(mappedBy = "directors", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Film> films = new HashSet<>();

    public String getFio() {
        return getSurname() + ". " + getName().charAt(0) + ". " + getPatronymic().charAt(0);
    }
}
