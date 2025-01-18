package ru.tde.films.Views.Sorts;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Actor;
import ru.tde.films.Views.Util.SortView;

import java.util.Set;

@Scope("prototype")
@Component
public class ActorSort extends SortView<Actor> {
    public ActorSort() {
        super(Actor.class, Set.of("surname", "name", "patronymic", "dateOfBirth", "gender", "country"));
    }
}
