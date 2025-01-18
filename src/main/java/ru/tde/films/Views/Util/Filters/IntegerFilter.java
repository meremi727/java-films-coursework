package ru.tde.films.Views.Util.Filters;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import org.javatuples.Pair;
import ru.tde.films.Repositories.Specifications.BaseSpecification;
import ru.tde.films.Views.Util.Annotation.AnnotationProcessor;

import java.util.function.Function;

public class IntegerFilter<T> extends HorizontalLayout implements Filter<T> {
    public IntegerFilter(Class<T> _class, String fieldName, Function<Pair<Integer, Integer>, BaseSpecification<T>> fabric) {
        var labelBase = AnnotationProcessor.getTranslation(_class, fieldName) + " ";
        from = new IntegerField(labelBase + "с");
        to = new IntegerField(labelBase + "по");
        from.setStep(1);
        to.setStep(1);

        this.fabric = fabric;
        setPadding(false);
        setSpacing(false);
        setMargin(false);
        add(from, to);
    }

    @Override
    public Component toComponent() { return this; }

    @Override
    public void clear() {
        from.clear();
        to.clear();
    }

    @Override
    public boolean isValid() {
        if (!isFilled())
            return true;

        if (!from.isEmpty() && !to.isEmpty())
            return from.getValue() < to.getValue();
        return true;
    }

    @Override
    public boolean isFilled() { return !from.isEmpty() || !to.isEmpty(); }

    @Override
    public BaseSpecification<T> getSpecificaion() {
        assert isValid();
        return fabric.apply(new Pair<>(from.getValue(), to.getValue()));
    }

    private final IntegerField from;
    private final IntegerField to;
    private final Function<Pair<Integer, Integer>, BaseSpecification<T>> fabric;
}
