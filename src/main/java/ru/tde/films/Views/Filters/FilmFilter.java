package ru.tde.films.Views.Filters;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Film;
import ru.tde.films.Repositories.Specifications.FilmSpecification;
import ru.tde.films.Views.Util.FilterView;
import ru.tde.films.Views.Util.Filters.*;
import java.util.List;


@Scope("prototype")
@Component
public class FilmFilter extends FilterView<Film> {
    public FilmFilter() {
        super(List.of(
                new StringFilter<>(Film.class, "title", t -> new FilmSpecification().hasTitle(t)),
                new DateFilter<>(Film.class, "dateReleased", p -> new FilmSpecification().releasedAfter(p.getValue0()).releasedBefore(p.getValue1())),
                new StringFilter<>(Film.class, "genre", g -> new FilmSpecification().hasGenre(g)),
                new StringFilter<>(Film.class, "country", c -> new FilmSpecification().hasCountry(c)),
                new IntegerFilter<>(Film.class, "timeDuration", p -> new FilmSpecification().hasDurationGreaterThanOrEqualTo(p.getValue0()).hasDurationLessThanOrEqualTo(p.getValue1()))
        ));
    }
}
