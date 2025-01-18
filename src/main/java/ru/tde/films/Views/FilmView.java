package ru.tde.films.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Actor;
import ru.tde.films.Domain.Film;
import ru.tde.films.Repositories.Specifications.BaseSpecification;
import ru.tde.films.Services.FilmService;
import ru.tde.films.Views.Util.*;

import java.text.SimpleDateFormat;
import java.util.List;


@Scope("prototype")
@Component
@Route(value = "films", layout = MainView.class)
public class FilmView extends View<Film> {
    @Autowired
    public FilmView(SortView<Film> sortView, FilterView<Film> filterView, ModalView<Film> modalView, FilmService filmService,  SimpleDateFormat dateFormatter) {
        super(sortView, filterView, modalView);
        this.filmService = filmService;
        this.dateFormatter = dateFormatter;
    }

    @Override
    protected void deleteEntity(Film entity) { filmService.deleteFilm(entity); }

    @Override
    protected void saveEntity(Film entity) { filmService.saveFilm(entity); }

    @Override
    protected CardView<Film> cardFabric(Film entity) {
        var card = new FilmCard();
        card.init(entity);
        return card;
    }

    @Override
    protected List<Film> updateData(BaseSpecification<Film> specification) { return filmService.getFiltered(specification); }

    private final FilmService filmService;
    private final SimpleDateFormat dateFormatter;

    private class FilmCard extends VerticalLayout implements CardView<Film> {

        @Override
        public void init(Film entity) {
            add(new H3(entity.getTitle()));

            addLabels(
                    "Дата выхода: " + dateFormatter.format(entity.getDateReleased()),
                    "Жанр: " + entity.getGenre(),
                    "Страна: " + entity.getCountry(),
                    "Продолжительность: " + entity.getTimeDuration()
            );

            var directors = new Details("Режиссеры");
            entity.getDirectors()
                    .stream()
                    .map(e -> new Paragraph(e.getFio()))
                    .forEach(directors::add);

            var actors = new Details("Актеры");
            entity.getActors()
                    .stream()
                    .map(e -> new Paragraph(e.getFio()))
                    .forEach(actors::add);

            var desc = new Details("Описание");
            desc.add(entity.getDescription());

            var editButton = new Button("✏\uFE0F", event -> openEditModal(entity));


            add(directors, actors, desc, editButton);
            setPadding(true);
            setSpacing(false);
        }

        private void addLabels(String... labels) {
            for(var label : labels)
                add(new Label(label));
        }

        @Override
        public com.vaadin.flow.component.Component toComponent() { return this; }
    }
}
