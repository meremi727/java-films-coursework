package ru.tde.films.Views.Sorts;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Repositories.Dto.DirectorDto;
import ru.tde.films.Views.Util.SortView;

import java.util.Set;

@Scope("prototype")
@Component
public class DirectorSort extends SortView<DirectorDto> {
    public DirectorSort() {
        super(DirectorDto.class, Set.of("surname", "name", "patronymic", "dateOfBirth", "dignity", "gender", "country"));
    }
}
