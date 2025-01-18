package ru.tde.films.Domain;

import java.util.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.*;
import ru.tde.films.Views.Util.Annotation.Translation;

@Entity
@Data
public class Comment {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Translation("Псевдоним")
    private String name;

    @Translation("Оценка")
    private Integer score;

    @Translation("Текст")
    private String text;

    @Translation("Дата")
    private Date dateWritten;
}
