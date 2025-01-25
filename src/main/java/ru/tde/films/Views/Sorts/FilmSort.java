package ru.tde.films.Views.Sorts;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Repositories.Dto.FilmDto;
import ru.tde.films.Views.Util.SortView;

import java.util.Set;

@Scope("prototype")
@Component
public class FilmSort extends SortView<FilmDto> {
    public FilmSort() {
        super(FilmDto.class, Set.of("title", "dateReleased", "genre", "country", "timeDuration"));
    }
}
