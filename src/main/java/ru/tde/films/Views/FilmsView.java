package ru.tde.films.Views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import ru.tde.films.Domain.Film;
import ru.tde.films.Repositories.Specifications.FilmSpecification;
import ru.tde.films.Repositories.Specifications.SearchCriteria;
import ru.tde.films.Services.FilmService;

import java.util.ArrayList;
import java.util.List;

@Route(value = "films", layout = MainView.class)
public class FilmsView extends VerticalLayout {

    private final VerticalLayout filmList = new VerticalLayout();

    private final TextField searchField = new TextField();
    private final Button searchButton = new Button("Найти", this::searchHandler);

    private final TextField genreFilter = new TextField("Жанр");
    private final TextField countryFilter = new TextField("Страна");
    private final DatePicker releaseDateFrom = new DatePicker("Дата выхода (с)");
    private final DatePicker releaseDateTo = new DatePicker("Дата выхода (по)");
    private final NumberField durationFrom = new NumberField("Продолжительность (мин. с)");
    private final NumberField durationTo = new NumberField("Продолжительность (мин. по)");

    private final Label pageLabel = new Label("0");

    private int currentPage = 0; // Текущая страница
    private static final int PAGE_SIZE = 10; // Количество фильмов на странице

    @Autowired
    private FilmService service;

    public FilmsView() {
        configureLayout();
        searchHandler(null);
    }

    private void searchHandler(ClickEvent<Button> event) {
        var specs = makeSpecifications();
        List<Film> films;
        if (specs != null)
            films = service.getFilteredPagination(specs, currentPage, PAGE_SIZE);
        else
            films = service.getPagination(currentPage, PAGE_SIZE);

        changeFilmsList(films);
        // update list
    }

    private void changeFilmsList(List<Film> films) {
        filmList.removeAll();
        for (Film film : films)
            filmList.add(new FilmCard(film));
    }

    private List<FilmSpecification> makeSpecifications() {
        return null;
    }

    private void configureLayout() {
        this.setAlignItems(Alignment.CENTER);

        durationFrom.setMin(0);
        durationTo.setMin(0);

        searchField.setPlaceholder("Название");
        var searchLayout = new HorizontalLayout();
        searchLayout.add(
                searchField,
                searchButton
        );
        searchLayout.setWidth("75%");
        searchLayout.setAlignSelf(Alignment.END);
        searchLayout.setFlexGrow(10, searchField);
        add(searchLayout);

        var filterOptions = new VerticalLayout();
        filterOptions.setWidth("20%");
        filterOptions.setAlignItems(Alignment.STRETCH);
        filterOptions.add(
                new Label("Фильтры"),
                countryFilter,
                genreFilter,
                releaseDateFrom,
                releaseDateTo,
                durationFrom,
                durationTo
        );

        var previousButton = new Button("Предыдущая", event -> {
            if (currentPage > 0) {
                currentPage--;
                searchHandler(null);
            }
        });

        var nextButton = new Button("Следующая", event -> {
            currentPage++;
            searchHandler(null);
        });

        var paginationLayout = new HorizontalLayout(
                previousButton,
                pageLabel,
                nextButton);

        var layout = new HorizontalLayout();
        layout.setAlignSelf(Alignment.START);
        layout.setWidth("100%");
        layout.setAlignItems(Alignment.START);
        layout.add(
                filterOptions,
                new VerticalLayout(filmList, paginationLayout)
                );
        add(layout);
    }

    private List<Film> fetchFilms(int offset, int limit) {
        // Здесь вы можете вызвать свой сервис для получения фильмов
        return List.of(
                new Film(),
                new Film(),
                new Film(),
                new Film(),
                new Film(),
                new Film(),
                new Film(),
                new Film(),
                new Film(),
                new Film(),
                new Film()
                // Здесь должны быть настоящие фильмы из вашего сервиса
        );
    }
}

class FilmCard extends VerticalLayout {
    public FilmCard(Film film) {
        super();
        var titleLink = new RouterLink(
                "Название: " + film.getTitle(),
                FilmView.class,
                film.getId()
        );

        add(
                titleLink,
                new H5("Дата выхода: " + film.getDateReleased()),
                new H5("Страна: " + film.getCountry()),
                new H5("Жанр: " + film.getGenre()),
                new H5("Продолжительность: " + film.getTimeDuration() + " ч.")
        );

        titleLink.getElement().getStyle().set("cursor", "pointer");
    }
}
