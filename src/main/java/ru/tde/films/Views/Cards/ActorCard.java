package ru.tde.films.Views.Cards;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Actor;
import ru.tde.films.Services.ActorService;
import ru.tde.films.Views.MainView;
import ru.tde.films.Views.Util.CardBase;

@Scope("prototype")
@Component
@Route(value = "actor/:actorId?", layout = MainView.class)
public class ActorCard extends CardBase<Actor> {
    @Autowired
    private ActorService service;

    @Override
    public void setParameter(BeforeEvent event, Integer parameter) {
        entity = service.getById(parameter);
        super.setParameter(event, parameter);
    }

    @Override
    protected VerticalLayout configureLayout() {
        var editButton = new Button("Изменить", e -> modal.openWithParameters(entity, true));

        var filmsList = new Div();
        filmsList.add(new H3("Фильмы:"));

        for (var film : entity.getFilms())
            filmsList.add(
                    new RouterLink(film.getTitle(), FilmCard.class, film.getId()),
                    new Paragraph()
            );

        return new VerticalLayout(
                        new Paragraph("Фамилия: " + entity.getSurname()),
                        new Paragraph("Имя: " + entity.getName()),
                        new Paragraph("Отчество: " + entity.getPatronymic()),
                        new Paragraph("Дата рождения: " + entity.getDateOfBirth()),
                        new Paragraph("Пол: " + entity.getGender()),
                        new Paragraph("Страна: " + entity.getCountry()),
                        filmsList,
                        editButton
                );
    }
}
