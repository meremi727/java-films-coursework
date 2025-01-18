package ru.tde.films.Views.Sorts;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Director;
import ru.tde.films.Views.Util.SortView;

import java.util.Set;

@Scope("prototype")
@Component
public class DirectorSort extends SortView<Director> {
    public DirectorSort() {
        super(Director.class, Set.of("surname", "name", "patronymic", "dateOfBirth", "dignity", "gender", "country"));
    }
}
