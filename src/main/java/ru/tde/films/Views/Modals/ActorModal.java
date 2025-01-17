package ru.tde.films.Views.Modals;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Actor;
import ru.tde.films.Domain.Film;
import ru.tde.films.Domain.Gender;
import ru.tde.films.Services.ActorService;
import ru.tde.films.Services.FilmService;
import ru.tde.films.Views.ActorsView;
import ru.tde.films.Views.Util.ModalBase;

import java.time.LocalDate;
import java.util.Date;
import java.util.function.Function;

@Scope("prototype")
@Component
public class ActorModal extends ModalBase<Actor> {

    public ActorModal() { setHeaderTitle("Актер"); }

    @Override
    protected FormLayout setupFields(Actor entity) {
        var form = createForm();
        return fillFormValues(form, entity);
    }

    @Override
    protected void apply(FormLayout form, Actor entity) {
        if (entity == null)
            entity = new Actor();

        entity.setName(name.getValue());
        entity.setSurname(surname.getValue());
        entity.setPatronymic(patronymic.getValue());
        entity.setCountry(country.getValue());
        entity.setGender(gender.getValue());
        entity.setDateOfBirth(reverseDateConverter.apply(dateOfBirth.getValue()));
        entity.setFilms(films.getValue().stream().toList());

        service.saveActor(entity);

        var finalEntity = entity;

        entity.getFilms().forEach(f -> {
            f.getActors().add(finalEntity);
            filmService.saveFilm(f);
        });

        UI.getCurrent().navigate(ActorsView.class);
    }

    @Override
    protected void discardChanges(FormLayout form, Actor entity) { fillFormValues(form, entity); }

    private FormLayout fillFormValues(FormLayout form, Actor entity) {
        if (entity == null) {
            name.clear();
            surname.clear();
            patronymic.clear();
            country.clear();
            dateOfBirth.clear();
            films.clear();
            gender.clear();
        }
        else {
            name.setValue(entity.getName());
            surname.setValue(entity.getSurname());
            patronymic.setValue(entity.getPatronymic());
            country.setValue(entity.getCountry());
            dateOfBirth.setValue(dateConverter.apply(entity.getDateOfBirth()));
            films.setValue(entity.getFilms());
            gender.setValue(entity.getGender());
        }

        return form;
    }

    private FormLayout createForm() {
        var form = new FormLayout();

        surname = new TextField("Фамилия");
        surname.setRequired(true);

        name = new TextField("Имя");
        name.setRequired(true);

        patronymic = new TextField("Отчество");
        patronymic.setRequired(true);

        gender = new ComboBox<>("Пол");
        gender.setItems(Gender.MALE, Gender.FEMALE);
        gender.setRequired(true);

        country = new TextField("Страна");
        country.setRequired(true);

        dateOfBirth = new DatePicker("Дата рождения");
        dateOfBirth.setRequired(true);

        films = new MultiSelectComboBox<>("Фильмы");
        films.setItems(filmService.getAll());
        films.setItemLabelGenerator(Film::getTitle);

        form.add(surname, name, patronymic, gender, country, dateOfBirth, films);

        return form;
    }

    @Autowired
    private ActorService service;

    @Autowired
    private FilmService filmService;

    @Autowired
    private Function<LocalDate, Date> reverseDateConverter;

    @Autowired
    private Function<Date, LocalDate> dateConverter;

    private TextField name, surname, patronymic, country;
    private DatePicker dateOfBirth;
    private MultiSelectComboBox<Film> films;
    private ComboBox<Gender> gender;
}
