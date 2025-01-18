package ru.tde.films.Views.Modals;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.DateRangeValidator;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Actor;
import ru.tde.films.Domain.Director;
import ru.tde.films.Domain.Film;
import ru.tde.films.Services.ActorService;
import ru.tde.films.Services.DirectorService;
import ru.tde.films.Services.FilmService;
import ru.tde.films.Views.Util.Annotation.AnnotationProcessor;
import ru.tde.films.Views.Util.ModalView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


@Scope("prototype")
@Component
public class FilmModal extends ModalView<Film> {

    @Autowired
    public FilmModal(ActorService actorService, DirectorService directorService) {
        super("Фильм");
        this.actorService = actorService;
        this.directorService = directorService;
        init();
    }

    protected void init() {
        var form = new FormLayout();

        addToBinder(id);

        directors.setItems(directorService.getAll());
        directors.setItemLabelGenerator(Director::getFio);

        actors.setItems(actorService.getAll());
        actors.setItemLabelGenerator(Actor::getFio);


        binder.forField(id).bind(Film::getId, Film::setId);
        binder.forField(title).asRequired("Обязательное поле").bind(Film::getTitle, Film::setTitle);
        binder.forField(genre).asRequired("Обязательное поле").bind(Film::getGenre, Film::setGenre);
        binder.forField(country).asRequired("Обязательное поле").bind(Film::getCountry, Film::setCountry);
        binder.forField(description).asRequired("Обязательное поле").bind(Film::getDescription, Film::setDescription);
        binder.forField(timeDuration)
                .asRequired("Обязательное поле")
                .withValidator(new IntegerRangeValidator("Неверная продолжительность", 1, Integer.MAX_VALUE))
                .bind(Film::getTimeDuration, Film::setTimeDuration);

        binder.forField(dateReleased)
                .asRequired("Обязательное поле")
                .withValidator(new DateRangeValidator("Неверная дата", LocalDate.MIN, LocalDate.now()))
                .withConverter(FilmModal::fromLocalDate, FilmModal::fromDate, "Ошибка преобразования даты")
                .bind(Film::getDateReleased, Film::setDateReleased);

        binder.forField(directors).bind(Film::getDirectors, Film::setDirectors);
        binder.forField(actors).bind(Film::getActors, Film::setActors);

        addToBinder(title);
        addToBinder(dateReleased);
        addToBinder(genre);
        addToBinder(country);
        addToBinder(description);
        addToBinder(timeDuration);
        addToBinder(directors);
        addToBinder(actors);

        form.add(title, dateReleased, genre, country, timeDuration, directors, actors, description);
        super.init(form);
    }

    @Override
    protected void prepareForEditing(Film entity) {
        tmpEntity = entity;
        binder.readBean(entity);
    }

    @Override
    protected void prepareForCreating() { binder.refreshFields(); }

    @Override
    protected void deleteButtonClick() { raiseDeleteEvent(tmpEntity); }

    @Override
    protected void applyButtonClick() {
        if (binder.isValid()) {
            var film = new Film();
            binder.writeBeanIfValid(film);
            raiseApplyEvent(film);
        }
    }

    private static String getLabel(String fieldName) {
        return AnnotationProcessor.getTranslation(Film.class, fieldName);
    }

    private static Date fromLocalDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private static LocalDate fromDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void addToBinder(AbstractField<?, ?> field) {
        field.addValueChangeListener(e -> binder.validate());
    }

    private final Binder<Film> binder = new Binder<>();

    private final IntegerField id = new IntegerField();
    private final TextField title = new TextField(getLabel("title"));
    private final DatePicker dateReleased = new DatePicker(getLabel("dateReleased"));
    private final TextField genre = new TextField(getLabel("genre"));
    private final TextField country = new TextField(getLabel("country"));
    private final TextArea description = new TextArea(getLabel("description"));
    private final IntegerField timeDuration = new IntegerField(getLabel("timeDuration"));
    private final MultiSelectComboBox<Director> directors = new MultiSelectComboBox<>(getLabel("directors"));
    private final MultiSelectComboBox<Actor> actors = new MultiSelectComboBox<>(getLabel("actors"));

    private Film tmpEntity;
    private final ActorService actorService;
    private final DirectorService directorService;
}
