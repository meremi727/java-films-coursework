package ru.tde.films.Views.Modals;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Actor;
import ru.tde.films.Domain.Director;
import ru.tde.films.Domain.Film;
import ru.tde.films.Domain.Person;
import ru.tde.films.Services.ActorService;
import ru.tde.films.Services.DirectorService;
import ru.tde.films.Services.FilmService;
import ru.tde.films.Views.FilmsView;
import ru.tde.films.Views.Util.ModalBase;

import java.time.Duration;
import java.time.ZoneId;
import java.util.Date;

@Scope("prototype")
@Component
public class FilmModal extends ModalBase<Film> {

    @Autowired
    private FilmService filmService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private DirectorService directorService;

    private TextField titleField;
    private DatePicker dateReleasedField;
    private TextField genreField;
    private TextField countryField;
    private TextArea descriptionField;
    private NumberField durationField;
    private MultiSelectComboBox<Director> directorsComboBox;
    private MultiSelectComboBox<Actor> actorsComboBox;

    public FilmModal() { setHeaderTitle("Фильм"); }

    @Override
    protected FormLayout setupFields(Film film) {
        var form = createForm();
        return fillFormValues(form, film);
    }

    @Override
    protected void apply(FormLayout form, Film film) {
        if (film == null)
            film = new Film();

        film.setTitle(titleField.getValue());
        film.setDateReleased(Date.from(dateReleasedField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        film.setGenre(genreField.getValue());
        film.setCountry(countryField.getValue());
        film.setTimeDuration(Duration.ofMinutes(durationField.getValue().longValue()));
        film.setDescription(descriptionField.getValue());
        film.setDirectors(directorsComboBox.getValue().stream().toList());
        film.setActors(actorsComboBox.getValue().stream().toList());

        filmService.saveFilm(film);

        UI.getCurrent().navigate(FilmsView.class);
    }

    @Override
    protected void discardChanges(FormLayout form, Film film) { fillFormValues(form, film); }

    @Override
    protected void delete(FormLayout form, Film entity) {
        filmService.deleteFilm(entity);
        UI.getCurrent().navigate(FilmsView.class);
    }

    private FormLayout createForm() {
        var form = new FormLayout();

        this.titleField = new TextField("Название");
        titleField.setRequired(true);

        this.dateReleasedField = new DatePicker("Дата выпуска");
        dateReleasedField.setRequired(true);

        this.genreField = new TextField("Жанр");
        genreField.setRequired(true);

        this.countryField = new TextField("Страна");
        countryField.setRequired(true);

        this.descriptionField = new TextArea("Описание");
        descriptionField.setRequired(true);
        descriptionField.setHeight("100%");

        this.durationField = new NumberField("Продолжительность (в минутах)");
        durationField.setRequired(true);
        durationField.setStep(1);

        this.directorsComboBox = new MultiSelectComboBox<Director>("Режиссеры");
        directorsComboBox.setRequired(true);
        directorsComboBox.setItemLabelGenerator(Person::getFio);
        directorsComboBox.setItems(directorService.getAll());

        this.actorsComboBox = new MultiSelectComboBox<Actor>("Актеры");
        actorsComboBox.setRequired(true);
        actorsComboBox.setItemLabelGenerator(Person::getFio);
        var ac = actorService.getAll();
        actorsComboBox.setItems(ac);

        form.add(
                titleField,
                dateReleasedField,
                genreField,
                countryField,
                durationField,
                directorsComboBox,
                actorsComboBox,
                descriptionField
        );

        form.setColspan(descriptionField, 2);

        return form;
    }

    private FormLayout fillFormValues(FormLayout form, Film film) {
        if (film == null) {
            titleField.clear();
            dateReleasedField.clear();
            genreField.clear();
            countryField.clear();
            durationField.clear();
            descriptionField.clear();
            directorsComboBox.clear();
            actorsComboBox.clear();
        }
        else {
            titleField.setValue(film.getTitle());
            dateReleasedField.setValue(film.getDateReleased().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            genreField.setValue(film.getGenre());
            countryField.setValue(film.getCountry());
            durationField.setValue((double) film.getTimeDuration().toMinutes());
            descriptionField.setValue(film.getDescription());
            directorsComboBox.setValue(film.getDirectors());
            actorsComboBox.setValue(film.getActors());
        }
        return form;
    }
}