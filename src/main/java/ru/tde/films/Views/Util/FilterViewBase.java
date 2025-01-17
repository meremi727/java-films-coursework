package ru.tde.films.Views.Util;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ru.tde.films.Repositories.Specifications.BaseSpecification;

import java.util.*;

public abstract class FilterViewBase<T> extends VerticalLayout {

    protected Details filtersBlock = new Details("Фильтры");

    protected Details sortingBlock = new Details("Сортировка");

    protected Button clearButton = new Button("Очистить", e -> clearForm());

    public FilterViewBase() {
        setSizeFull();
        setPadding(false);
        add(
                new Button("Найти", e -> raiseSearchEvent()),
                sortingBlock,
                filtersBlock,
                clearButton
        );
    }

    public void addSearchListener(Runnable listener) { searchListeners.add(listener); }

    private void raiseSearchEvent() {
        for (var listener : searchListeners)
            if (listener != null)
                listener.run();
    }

    public abstract BaseSpecification<T> getSpecification();

    protected abstract void clearForm();

    private final List<Runnable> searchListeners = new ArrayList<>();
}
