package ru.tde.films.Views.Filters;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Actor;
import ru.tde.films.Repositories.Specifications.BaseSpecification;
import ru.tde.films.Views.Util.FilterViewBase;

@Scope("prototype")
@Component
public class ActorFilterView extends FilterViewBase<Actor> {
    @Override
    public BaseSpecification<Actor> getSpecification() {
        return new BaseSpecification<>();
    }

    @Override
    protected void clearForm() {

    }
}
