package ru.tde.films.Domain;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Builder
@AllArgsConstructor
public class Actor  {

    private Actor() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String country;
    private String surname;
    private String name;
    private String patronymic;
    private Date dateOfBirth;

    public String getFio() {
        return getSurname() + ". " + getName().charAt(0) + ". " + getPatronymic().charAt(0);
    }
}
