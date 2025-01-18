package ru.tde.films.Views.Util;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ru.tde.films.Repositories.Specifications.BaseSpecification;
import ru.tde.films.Views.Util.Filters.Filter;

import java.util.*;

public class FilterView<T> extends VerticalLayout {

    public FilterView(List<Filter<T>> filters) {
        this.filters = filters;
        setSizeUndefined();
        setPadding(false);
        setSpacing(false);
        add(filters.stream().map(Filter::toComponent).toList());
    }

    public void clear() { filters.forEach(Filter::clear); }

    public boolean isValid() { return filters.stream().allMatch(Filter::isValid); }

    public BaseSpecification<T> getSpecification() {
        assert isValid();

        return filters.stream()
                .map(Filter::getSpecificaion)
                .reduce(BaseSpecification::add)
                .orElseGet(BaseSpecification::new);
    }

    private final List<Filter<T>> filters;
}
