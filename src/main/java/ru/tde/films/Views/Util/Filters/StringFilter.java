package ru.tde.films.Views.Util.Filters;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import ru.tde.films.Repositories.Specifications.BaseSpecification;
import ru.tde.films.Views.Util.Annotation.AnnotationProcessor;

import java.util.function.Function;

public class StringFilter<T> extends VerticalLayout implements Filter<T> {
    public StringFilter(Class<T> _class, String fieldName, Function<String, BaseSpecification<T>> fabric) {
        field = new TextField(AnnotationProcessor.getTranslation(_class, fieldName));
        this.fabric = fabric;
        setPadding(false);
        setSpacing(false);
        setMargin(false);
        add(field);
    }

    @Override
    public Component toComponent() {
        return this;
    }

    @Override
    public void clear() {
        field.clear();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean isFilled() {
        return !field.getValue().isBlank();
    }

    @Override
    public BaseSpecification<T> getSpecificaion() {
        return fabric.apply(field.getValue());
    }

    private final TextField field;
    private final Function<String, BaseSpecification<T>> fabric;
}
