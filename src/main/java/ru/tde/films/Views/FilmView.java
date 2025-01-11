package ru.tde.films.Views;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tde.films.Domain.Director;
import ru.tde.films.Domain.Film;
import ru.tde.films.Domain.Person;
import ru.tde.films.Services.FilmService;

@Route(value = "film/:filmId?", layout = MainView.class)
public class FilmView extends VerticalLayout implements HasUrlParameter<Integer>{
    @Autowired
    private FilmService filmService;
    private Film film;


    public FilmView() {

    }

    private void configureLayout() {
        add(
                new H1("Название: " + film.getTitle()),
                new H4("Дата выхода: " + film.getDateReleased()),
                new H4("Жанр: " + film.getGenre()),
                new H4("Страна: " + film.getCountry()),
                new H4("Продолжительность: " + film.getTimeDuration()),
                new H4("Режиссеры: " + film.getDirectors().stream().map(Director::getFio).reduce((a, b) -> a + ", " + b)),
                new H4("Актеры: " + film.getActors().stream().map(Person::getFio).reduce((a, b) -> a + ", " + b)),
                new H4("Описание:"),
                new H5(film.getDescription())
        );
    }

    @Override
    public void setParameter(BeforeEvent event, Integer parameter) {
        film = filmService.getById(parameter);
        configureLayout();
    }
}
