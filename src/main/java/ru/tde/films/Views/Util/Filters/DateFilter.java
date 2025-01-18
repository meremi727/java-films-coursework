package ru.tde.films.Views.Util.Filters;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.javatuples.Pair;
import ru.tde.films.Repositories.Specifications.BaseSpecification;
import ru.tde.films.Views.Util.Annotation.AnnotationProcessor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

public class DateFilter<T> extends VerticalLayout implements Filter<T> {
    public DateFilter(Class<T> _class, String fieldName, Function<Pair<Date, Date>, BaseSpecification<T>> fabric) {
        var labelBase = AnnotationProcessor.getTranslation(_class, fieldName) + " ";
        from = new DatePicker(labelBase + "с");
        to = new DatePicker(labelBase + "по");

        this.fabric = fabric;
        setPadding(false);
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
            return from.getValue().isBefore(to.getValue());

        return true;
    }

    @Override
    public boolean isFilled() { return !from.isEmpty() || !to.isEmpty(); }

    @Override
    public BaseSpecification<T> getSpecificaion() {
        assert isValid();
        return fabric.apply(new Pair<>(convert(from.getValue()), convert(to.getValue())));
    }

    private static Date convert(LocalDate date) {
        if (date == null)
            return null;
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private final DatePicker from;
    private final DatePicker to;
    private final Function<Pair<Date, Date>, BaseSpecification<T>> fabric;
}
