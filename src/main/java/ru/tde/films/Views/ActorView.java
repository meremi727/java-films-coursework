package ru.tde.films.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Actor;
import ru.tde.films.Repositories.Specifications.BaseSpecification;
import ru.tde.films.Services.ActorService;
import ru.tde.films.Views.Filters.ActorFilter;
import ru.tde.films.Views.Modals.ActorModal;
import ru.tde.films.Views.Sorts.ActorSort;
import ru.tde.films.Views.Util.*;

import java.text.SimpleDateFormat;
import java.util.List;

@Scope("prototype")
@Component
@Route(value = "actors", layout = MainView.class)
public class ActorView extends View<Actor> {
    @Autowired
    public ActorView(ActorSort sortView, ActorFilter filterView, ActorModal modalView, ActorService service, SimpleDateFormat dateFormatter) {
        super(sortView, filterView, modalView);
        this.service = service;
        this.dateFormatter = dateFormatter;
    }

    @Override
    protected void deleteEntity(Actor entity) { service.deleteActor(entity); }

    @Override
    protected void saveEntity(Actor entity) { service.saveActor(entity); }

    @Override
    protected List<Actor> updateData(BaseSpecification<Actor> specification) { return service.getFiltered(specification); }

    @Override
    protected CardView<Actor> cardFabric(Actor entity) {
        var card = new ActorCard();
        card.init(entity);
        return card;
    }

    private final ActorService service;
    private final SimpleDateFormat dateFormatter;

    private class ActorCard extends VerticalLayout implements CardView<Actor> {
        @Override
        public void init(Actor actor) {
            add(new H3(actor.getFio()));

            addLabels(
                    "Фамилия: " + actor.getSurname(),
                    "Имя: " + actor.getName(),
                    "Отчество: " + actor.getPatronymic(),
                    "Дата рождения: " + dateFormatter.format(actor.getDateOfBirth()),
                    "Пол: " + actor.getGender(),
                    "Страна: " + actor.getCountry()
            );

            var editButton = new Button("✏\uFE0F", event -> openEditModal(actor));
            var films = new Details("Фильмы");
            actor.getFilms()
                    .stream()
                    .map(e -> new Paragraph(e.getTitle()))
                    .forEach(films::add);

            add(films, editButton);
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
