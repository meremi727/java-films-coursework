package ru.tde.films.Views.Util;

import com.vaadin.flow.component.Component;

public interface CardView<T> {
    void init(T entity);

    Component toComponent();
}
