package ru.tde.films.Views;

import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.tde.films.Domain.Film;
import ru.tde.films.Repositories.Specifications.BaseSpecification;
import ru.tde.films.Services.FilmService;
import ru.tde.films.Views.Cards.ActorCard;
import ru.tde.films.Views.Cards.DirectorCard;
import ru.tde.films.Views.Cards.FilmCard;
import ru.tde.films.Views.Filters.FilmFilterView;
import ru.tde.films.Views.Modals.FilmModal;
import ru.tde.films.Views.Util.BaseView;
import ru.tde.films.Views.Util.PaginationView;

import java.text.SimpleDateFormat;
import java.util.List;


@Scope("prototype")
@Component
@Route(value = "films", layout = MainView.class)
public class FilmsView extends BaseView<Film, FilmFilterView, FilmModal> {

    private final FilmService service;

    private final SimpleDateFormat dateFormatter;

    @Autowired
    public FilmsView(FilmService service, FilmFilterView filterView, FilmModal modal, PaginationView paginationView, SimpleDateFormat formatter) {
        super(filterView, modal, paginationView);
        this.setTitle("Фильмы");
        this.service = service;
        this.dateFormatter = formatter;
        setMaxPage(service.getPagesCount(getPageSize()));
        forceUpdateList();
    }

    @Override
    protected List<Film> getEntities(int page, int pageSize, BaseSpecification<Film> specification) {
        return service.getFilteredPagination(specification, page, pageSize);
    }

    @Override
    protected VerticalLayout listCardFabric(Film film) {
        var result = new VerticalLayout();
        result.setSpacing(false);
        result.setPadding(false);
        result.setMargin(false);
        var titleLink = new RouterLink(film.getTitle(), FilmCard.class, film.getId());
        var title = new H2(titleLink);
        result.add(title);
        result.setMargin(false);
        var releaseDate = new Paragraph("Дата выпуска: " + dateFormatter.format(film.getDateReleased()));
        var genre = new Paragraph("Жанр: " + film.getGenre());
        var country = new Paragraph("Страна: " + film.getCountry());
        var duration = new Paragraph("Продолжительность: " + film.getTimeDuration().toHours() + " ч "
                + film.getTimeDuration().toMinutesPart() + " мин");
        result.add(releaseDate, genre, country, duration);

        var description = new Div();
        description.setText(film.getDescription().substring(0, Math.min(film.getDescription().length() - 1, 200)) + "...");
        description.setWhiteSpace(HasText.WhiteSpace.NORMAL);
        result.add(description);

        var directorsBlock = new Details("Режиссеры");
        film.getDirectors()
                .stream()
                .map(i -> new RouterLink(i.getFio(), DirectorCard.class, i.getId()))
                .forEach(directorsBlock::add);
        result.add(directorsBlock);

        var actorsBlock = new Details("Актеры");
        film.getActors()
                .stream()
                .map(actor -> new RouterLink(actor.getFio(), ActorCard.class, actor.getId()))
                .forEach(i -> {actorsBlock.add(i); actorsBlock.add(new Paragraph());});
        result.add(actorsBlock);

        return result;
    }
}
