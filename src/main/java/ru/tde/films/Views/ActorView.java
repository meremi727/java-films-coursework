package ru.tde.films.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Actor;
import ru.tde.films.Repositories.Dto.ActorDto;
import ru.tde.films.Repositories.Specifications.BaseSpecification;
import ru.tde.films.Services.BigService;
import ru.tde.films.Views.Filters.ActorFilter;
import ru.tde.films.Views.Modals.ActorModal;
import ru.tde.films.Views.Sorts.ActorSort;
import ru.tde.films.Views.Util.*;

import java.text.SimpleDateFormat;
import java.util.List;

@Scope("prototype")
@Component
@Route(value = "actors", layout = MainView.class)
public class ActorView extends View<Actor, ActorDto> {
    @Autowired
    public ActorView(ActorSort sortView, ActorFilter filterView, ActorModal modalView, BigService service, SimpleDateFormat dateFormatter) {
        super(sortView, filterView, modalView);
        this.service = service;
        this.dateFormatter = dateFormatter;
        initForceUpdate();
    }

    @Override
    protected void deleteEntity(ActorDto entity) { service.deleteActor(entity); }

    @Override
    protected void saveEntity(ActorDto entity) { service.saveActor(entity); }

    @Override
    protected List<ActorDto> updateData(BaseSpecification<Actor, ActorDto> specification) { return service.getFilteredActors(specification); }

    @Override
    protected CardView<ActorDto> cardFabric(ActorDto entity) {
        var card = new ActorCard();
        card.init(entity);
        return card;
    }

    private final BigService service;
    private final SimpleDateFormat dateFormatter;

    private class ActorCard extends VerticalLayout implements CardView<ActorDto> {
        @Override
        public void init(ActorDto actor) {
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

            add(editButton);
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
