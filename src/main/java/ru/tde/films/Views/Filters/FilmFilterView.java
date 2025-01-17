package ru.tde.films.Views.Filters;

import java.time.Duration;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.tde.films.Domain.Film;
import ru.tde.films.Repositories.Specifications.*;
import ru.tde.films.Views.Util.FilterViewBase;
import ru.tde.films.Views.Util.SortingFilter;

@Scope("prototype")
@Component
public class FilmFilterView extends FilterViewBase<Film> {

    private TextField titleField;
    private TextField countryField;
    private TextField genreField;
    private DatePicker releasedFrom;
    private DatePicker releasedTo;
    private NumberField durationFrom;
    private NumberField durationTo;

    private SortingFilter<Film> sortingView;

    public FilmFilterView() {
        super();
        configureFiltersFields();
        configureSortingView();
    }

    @Override
    public BaseSpecification<Film> getSpecification() {
        FilmSpecification spec = new FilmSpecification()
            .hasCountry(countryField.getValue())
            .hasGenre(genreField.getValue())
            .hasTitle(titleField.getValue())
            .withSort(sortingView.getOrder());

        if (releasedFrom.getValue() != null)
            spec.releasedAfter(Date.from(releasedFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        if (releasedTo.getValue() != null)
            spec.releasedBefore(Date.from(releasedTo.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        if (durationFrom.getValue() != null)
            spec.hasDurationGreaterThanOrEqualTo(Duration.ofMinutes(durationFrom.getValue().longValue()));
        if (durationTo.getValue() != null)
            spec.hasDurationLessThanOrEqualTo(Duration.ofMinutes(durationTo.getValue().longValue()));

        return spec;
    }

    private void configureFiltersFields() {
        titleField = new TextField("Название");
        countryField = new TextField("Страна");
        genreField = new TextField("Жанр");
        releasedFrom = new DatePicker("Дата выпуска (с)");
        releasedTo = new DatePicker("Дата выпуска (по)");
        durationFrom = new NumberField("Продолжительность (с)");
        durationTo = new NumberField("Продолжительность (по)");

        titleField.setSizeFull();
        countryField.setSizeFull();
        genreField.setSizeFull();
        releasedFrom.setSizeFull();
        releasedTo.setSizeFull();
        durationTo.setSizeFull();
        durationFrom.setSizeFull();

        durationFrom.setMin(0);
        durationTo.setMin(0);

        var filtersLayout = new VerticalLayout(
                titleField,
                countryField,
                genreField,
                releasedFrom,
                releasedTo,
                durationFrom,
                durationTo
        );

        filtersLayout.setSizeFull();
        filtersLayout.setPadding(false);
        filtersLayout.setSpacing(false);

        this.filtersBlock.add(filtersLayout);
    }

    private void configureSortingView() {
        sortingView = new SortingFilter<>(Map.of(
                "title", "Название",
                "dateReleased", "Дата выпуска",
                "genre", "Жанр",
                "country", "Страна",
                "timeDuration", "Продолжительность"
        ));
        this.sortingBlock.add(sortingView);
    }

    @Override
    protected void clearForm() {
        titleField.clear();
        countryField.clear();
        genreField.clear();
        releasedFrom.clear();
        releasedTo.clear();
        durationFrom.clear();
        durationTo.clear();
    }
}
