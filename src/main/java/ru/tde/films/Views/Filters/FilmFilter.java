package ru.tde.films.Views.Filters;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Film;
import ru.tde.films.Repositories.Dto.FilmDto;
import ru.tde.films.Repositories.Specifications.FilmSpecification;
import ru.tde.films.Views.Util.FilterView;
import ru.tde.films.Views.Util.Filters.*;
import java.util.List;


@Scope("prototype")
@Component
public class FilmFilter extends FilterView<Film, FilmDto> {
    public FilmFilter() {
        super(List.of(
                new StringFilter<>(FilmDto.class, "title", t -> new FilmSpecification().hasTitle(t)),
                new DateFilter<>(FilmDto.class, "dateReleased", p -> new FilmSpecification().releasedAfter(p.getValue0()).releasedBefore(p.getValue1())),
                new StringFilter<>(FilmDto.class, "genre", g -> new FilmSpecification().hasGenre(g)),
                new StringFilter<>(FilmDto.class, "country", c -> new FilmSpecification().hasCountry(c)),
                new IntegerFilter<>(FilmDto.class, "timeDuration", p -> new FilmSpecification().hasDurationGreaterThanOrEqualTo(p.getValue0()).hasDurationLessThanOrEqualTo(p.getValue1()))
        ));
    }
}
