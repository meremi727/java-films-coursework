package ru.tde.films.Views;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Actor;
import ru.tde.films.Repositories.Specifications.BaseSpecification;
import ru.tde.films.Services.ActorService;
import ru.tde.films.Views.Cards.ActorCard;
import ru.tde.films.Views.Cards.FilmCard;
import ru.tde.films.Views.Filters.ActorFilterView;
import ru.tde.films.Views.Modals.ActorModal;
import ru.tde.films.Views.Util.BaseView;
import ru.tde.films.Views.Util.PaginationView;

import java.text.SimpleDateFormat;
import java.util.List;

@Scope("prototype")
@Component
@Route(value = "actors", layout = MainView.class)
public class ActorsView extends BaseView<Actor, ActorFilterView, ActorModal> {
    private final ActorService service;
    private final SimpleDateFormat dateFormatter;

    public ActorsView(ActorService service, ActorFilterView filterView, ActorModal modal, PaginationView paginationView, SimpleDateFormat formatter) {
        super(filterView, modal, paginationView);
        setTitle("Актеры");
        this.service = service;
        this.dateFormatter = formatter;
        setMaxPage(service.getPagesCount(getPageSize()));
        forceUpdateList();
    }

    @Override
    protected List<Actor> getEntities(int page, int pageSize, BaseSpecification<Actor> specification) {
        return service.getFilteredPagination(specification, page, pageSize);
    }

    @Override
    protected VerticalLayout listCardFabric(Actor actor) {
        var result = new VerticalLayout();
        result.setSpacing(false);

        var fioLink = new H2(new RouterLink(actor.getFio(), ActorCard.class, actor.getId()));
        result.add(fioLink);

        var surname = new Paragraph("Фамилия: " + actor.getSurname());
        var name = new Paragraph("Имя: " + actor.getName());
        var patronymic = new Paragraph("Отчество: " + actor.getPatronymic());
        var dateOfBirth = new Paragraph("Дата рождения: " + dateFormatter.format(actor.getDateOfBirth()));
        var gender = new Paragraph("Пол: " + actor.getGender());
        var country = new Paragraph("Страна: " + actor.getCountry());

        result.add(surname, name, patronymic, dateOfBirth, gender, country);

        var filmsBlock = new Details("Фильмы");
        actor.getFilms()
                .stream()
                .map(i -> new RouterLink(i.getTitle(), FilmCard.class, i.getId()))
                .forEach(filmsBlock::add);

        result.add(filmsBlock);

        return result;
    }
}
