package ru.tde.films.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Director;
import ru.tde.films.Repositories.Specifications.BaseSpecification;
import ru.tde.films.Services.DirectorService;
import ru.tde.films.Views.Util.*;

import java.text.SimpleDateFormat;
import java.util.List;

@Scope("prototype")
@Component
@Route(value = "directors", layout = MainView.class)
public class DirectorView extends View<Director> {
    @Autowired
    public DirectorView(SortView<Director> sortView, FilterView<Director> filterView, ModalView<Director> modalView, DirectorService service, SimpleDateFormat dateFormatter) {
        super(sortView, filterView, modalView);
        this.service = service;
        this.dateFormatter = dateFormatter;
    }

    @Override
    protected void deleteEntity(Director entity) { service.deleteDirector(entity); }

    @Override
    protected void saveEntity(Director entity) { service.saveDirector(entity); }

    @Override
    protected CardView<Director> cardFabric(Director entity) {
        var card = new DirectorCard();
        card.init(entity);
        return card;
    }

    @Override
    protected List<Director> updateData(BaseSpecification<Director> specification) { return service.getFiltered(specification); }

    private final SimpleDateFormat dateFormatter;
    private final DirectorService service;

    private class DirectorCard extends VerticalLayout implements CardView<Director> {
        @Override
        public void init(Director director) {
            add(new H3(director.getFio()));

            addLabels(
                    "Фамилия: " + director.getSurname(),
                    "Имя: " + director.getName(),
                    "Отчество: " + director.getPatronymic(),
                    "Дата рождения: " + dateFormatter.format(director.getDateOfBirth()),
                    "Пол: " + director.getGender(),
                    "Страна: " + director.getCountry(),
                    "Титул: " + director.getDignity()
            );

            var editButton = new Button("✏\uFE0F", event -> openEditModal(director));
            var films = new Details("Фильмы");
            director.getFilms()
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
