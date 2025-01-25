package ru.tde.films.Domain;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Builder
@AllArgsConstructor
public class Comment {

    private Comment() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Integer score;
    private String text;
    private Date dateWritten;
}
