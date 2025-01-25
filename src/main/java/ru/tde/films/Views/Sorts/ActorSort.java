package ru.tde.films.Views.Sorts;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Repositories.Dto.ActorDto;
import ru.tde.films.Views.Util.SortView;

import java.util.Set;

@Scope("prototype")
@Component
public class ActorSort extends SortView<ActorDto> {
    public ActorSort() {
        super(ActorDto.class, Set.of("surname", "name", "patronymic", "dateOfBirth", "gender", "country"));
    }
}
