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
import ru.tde.films.Repositories.Dto.DirectorDto;
import ru.tde.films.Repositories.Specifications.BaseSpecification;
import ru.tde.films.Services.BigService;
import ru.tde.films.Views.Filters.DirectorFilter;
import ru.tde.films.Views.Modals.DirectorModal;
import ru.tde.films.Views.Sorts.DirectorSort;
import ru.tde.films.Views.Util.*;

import java.text.SimpleDateFormat;
import java.util.List;

@Scope("prototype")
@Component
@Route(value = "directors", layout = MainView.class)
public class DirectorView extends View<Director, DirectorDto> {
    @Autowired
    public DirectorView(DirectorSort sortView, DirectorFilter filterView, DirectorModal modalView, BigService service, SimpleDateFormat dateFormatter) {
        super(sortView, filterView, modalView);
        this.service = service;
        this.dateFormatter = dateFormatter;
        initForceUpdate();
    }

    @Override
    protected void deleteEntity(DirectorDto entity) { service.deleteDirector(entity); }

    @Override
    protected void saveEntity(DirectorDto entity) { service.saveDirector(entity); }

    @Override
    protected CardView<DirectorDto> cardFabric(DirectorDto entity) {
        var card = new DirectorCard();
        card.init(entity);
        return card;
    }

    @Override
    protected List<DirectorDto> updateData(BaseSpecification<Director, DirectorDto> specification) { return service.getFilteredDirectors(specification); }

    private final SimpleDateFormat dateFormatter;
    private final BigService service;

    private class DirectorCard extends VerticalLayout implements CardView<DirectorDto> {
        @Override
        public void init(DirectorDto director) {
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
