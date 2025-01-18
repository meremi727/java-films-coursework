package ru.tde.films.Views.Util.Filters;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ru.tde.films.Repositories.Specifications.BaseSpecification;
import ru.tde.films.Views.Util.Annotation.AnnotationProcessor;

import java.util.Objects;
import java.util.function.Function;

public class EnumFilter<T, TEnum extends Enum<TEnum>> extends VerticalLayout implements Filter<T> {
    public EnumFilter(Class<T> _class, String fieldName, Class<TEnum> _class2, Function<TEnum, BaseSpecification<T>> fabric) {
        comboBox = new ComboBox<TEnum>(AnnotationProcessor.getTranslation(_class, fieldName));
        comboBox.setItemLabelGenerator(Objects::toString);
        comboBox.setItems(_class2.getEnumConstants());

        this.fabric = fabric;
        setPadding(false);
        setSpacing(false);
        setMargin(false);
        add(comboBox);
    }

    @Override
    public Component toComponent() { return this; }

    @Override
    public void clear() { comboBox.clear(); }

    @Override
    public boolean isValid() { return true; }

    @Override
    public boolean isFilled() { return comboBox.getEmptyValue() != comboBox.getValue(); }

    @Override
    public BaseSpecification<T> getSpecificaion() { return this.fabric.apply(comboBox.getValue()); }

    private final ComboBox<TEnum> comboBox;
    private final Function<TEnum, BaseSpecification<T>> fabric;
}
