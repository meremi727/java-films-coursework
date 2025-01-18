package ru.tde.films.Views.Filters;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Actor;
import ru.tde.films.Domain.Gender;
import ru.tde.films.Repositories.Specifications.ActorSpecification;
import ru.tde.films.Views.Util.Filters.DateFilter;
import ru.tde.films.Views.Util.Filters.EnumFilter;
import ru.tde.films.Views.Util.FilterView;
import ru.tde.films.Views.Util.Filters.StringFilter;

import java.util.*;

@Scope("prototype")
@Component
public class ActorFilter extends FilterView<Actor> {
    public ActorFilter() {
        super(List.of(
                new StringFilter<>(Actor.class, "surname", s -> new ActorSpecification().hasSurname(s)),
                new StringFilter<>(Actor.class, "name", s -> new ActorSpecification().hasName(s)),
                new StringFilter<>(Actor.class, "patronymic", s -> new ActorSpecification().hasPatronymic(s)),
                new DateFilter<>(Actor.class, "dateOfBirth", p -> new ActorSpecification().dateOfBirthAfter(p.getValue0()).dateOfBirthBefore(p.getValue1())),
                new EnumFilter<>(Actor.class, "gender", Gender.class, g -> new ActorSpecification().hasGender(g)),
                new StringFilter<>(Actor.class, "country", c -> new ActorSpecification().hasCountry(c))
        ));
    }
}
