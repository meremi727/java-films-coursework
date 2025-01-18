package ru.tde.films.Views.Filters;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Director;
import ru.tde.films.Domain.Gender;
import ru.tde.films.Repositories.Specifications.DirectorSpecification;
import ru.tde.films.Views.Util.FilterView;
import ru.tde.films.Views.Util.Filters.*;
import java.util.List;


@Scope("prototype")
@Component
public class DirectorFilter extends FilterView<Director> {
    public DirectorFilter() {
        super(List.of(
                new EnumFilter<>(Director.class, "gender", Gender.class, g -> new DirectorSpecification().hasGender(g)),
                new StringFilter<>(Director.class, "country", c -> new DirectorSpecification().hasCountry(c)),
                new StringFilter<>(Director.class, "surname", c -> new DirectorSpecification().hasSurname(c)),
                new StringFilter<>(Director.class, "name", c -> new DirectorSpecification().hasName(c)),
                new StringFilter<>(Director.class, "patronymic", c -> new DirectorSpecification().hasPatronymic(c)),
                new DateFilter<>(Director.class, "dateOfBirth", p -> new DirectorSpecification().dateOfBirthAfter(p.getValue0()).dateOfBirthBefore(p.getValue1())),
                new StringFilter<>(Director.class, "dignity", c -> new DirectorSpecification().hasDignity(c))
        ));
    }
}
