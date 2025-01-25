package ru.tde.films.Views.Util.Filters;

import com.vaadin.flow.component.Component;
import ru.tde.films.Repositories.Specifications.BaseSpecification;


public interface Filter<T, TDto> {
    Component toComponent();

    void clear();

    boolean isValid();

    boolean isFilled();

    BaseSpecification<T, TDto> getSpecificaion();
}

