package ru.tde.films.Views.Filters;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.*;
import ru.tde.films.Repositories.Specifications.ActorSpecification;
import ru.tde.films.Repositories.Dto.ActorDto;
import ru.tde.films.Views.Util.Filters.*;
import ru.tde.films.Views.Util.FilterView;

import java.util.*;

@Scope("prototype")
@Component
public class ActorFilter extends FilterView<Actor, ActorDto> {
    public ActorFilter() {
        super(List.of(
                new StringFilter<>(ActorDto.class, "surname", s -> new ActorSpecification().hasSurname(s)),
                new StringFilter<>(ActorDto.class, "name", s -> new ActorSpecification().hasName(s)),
                new StringFilter<>(ActorDto.class, "patronymic", s -> new ActorSpecification().hasPatronymic(s)),
                new DateFilter<>(ActorDto.class, "dateOfBirth", p -> new ActorSpecification().dateOfBirthAfter(p.getValue0()).dateOfBirthBefore(p.getValue1())),
                new EnumFilter<>(ActorDto.class, "gender", Gender.class, g -> new ActorSpecification().hasGender(g)),
                new StringFilter<>(ActorDto.class, "country", c -> new ActorSpecification().hasCountry(c))
        ));
    }
}
