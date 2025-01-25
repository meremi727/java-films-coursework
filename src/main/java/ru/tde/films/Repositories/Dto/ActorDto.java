package ru.tde.films.Repositories.Dto;

import java.util.*;
import lombok.*;
import ru.tde.films.Domain.Gender;
import ru.tde.films.Views.Util.Annotation.Translation;


@Data
@Builder
@AllArgsConstructor
public class ActorDto  {

    private ActorDto() {}

    private Integer id;

    @Translation("Пол")
    private Gender gender;

    @Translation("Страна")
    private String country;

    @Translation("Фамилия")
    private String surname;

    @Translation("Имя")
    private String name;

    @Translation("Отчество")
    private String patronymic;

    @Translation("Дата рождения")
    private Date dateOfBirth;

    public String getFio() {
        return getSurname() + ". " + getName().charAt(0) + ". " + getPatronymic().charAt(0);
    }
}