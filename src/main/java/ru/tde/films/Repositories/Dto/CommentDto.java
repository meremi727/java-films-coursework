package ru.tde.films.Repositories.Dto;

import java.util.*;
import lombok.*;
import ru.tde.films.Views.Util.Annotation.Translation;


@Data
@Builder
@AllArgsConstructor
public class CommentDto {

    private CommentDto() {}

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