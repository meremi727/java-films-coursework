package ru.tde.films.Repositories.Dto;

import java.util.*;
import lombok.*;
import ru.tde.films.Views.Util.Annotation.Translation;


@Data
@Builder
@AllArgsConstructor
public class FilmDto {

    private FilmDto() {}

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
    private Set<DirectorDto> directors = new HashSet<>();

    @Translation("Актеры")
    private Set<ActorDto> actors = new HashSet<>();

    @Translation("Комментарии")
    private Set<CommentDto> comments = new HashSet<>();
}