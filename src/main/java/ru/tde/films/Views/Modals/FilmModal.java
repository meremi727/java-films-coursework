package ru.tde.films.Views.Modals;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.*;
import ru.tde.films.Repositories.Dto.*;
import ru.tde.films.Services.BigService;
import ru.tde.films.Views.Util.Annotation.AnnotationProcessor;
import ru.tde.films.Views.Util.ModalView;

import java.time.*;
import java.util.Date;
import java.util.function.Function;


@Scope("prototype")
@Component
public class FilmModal extends ModalView<FilmDto> {

    @Autowired
    public FilmModal(BigService service, Function<Date, LocalDate> f1, Function<LocalDate, Date> f2) {
        super("Фильм");
        this.service = service;
        this.toDate = f1;
        this.toReverseDate = f2;
        init();
    }

    protected void init() {
        var form = new FormLayout();

        addToBinder(id);

        directors.setItems(service.getAllDirectors());
        directors.setItemLabelGenerator(DirectorDto::getFio);

        actors.setItems(service.getAllActors());
        actors.setItemLabelGenerator(ActorDto::getFio);


        binder.forField(id).bind(FilmDto::getId, FilmDto::setId);
        binder.forField(title).asRequired("Обязательное поле").bind(FilmDto::getTitle, FilmDto::setTitle);
        binder.forField(genre).asRequired("Обязательное поле").bind(FilmDto::getGenre, FilmDto::setGenre);
        binder.forField(country).asRequired("Обязательное поле").bind(FilmDto::getCountry, FilmDto::setCountry);
        binder.forField(description).asRequired("Обязательное поле").bind(FilmDto::getDescription, FilmDto::setDescription);
        binder.forField(timeDuration)
                .asRequired("Обязательное поле")
                .withValidator(new IntegerRangeValidator("Неверная продолжительность", 1, Integer.MAX_VALUE))
                .bind(FilmDto::getTimeDuration, FilmDto::setTimeDuration);

        binder.forField(dateReleased)
                .asRequired("Обязательное поле")
                .withValidator(new DateRangeValidator("Неверная дата", LocalDate.MIN, LocalDate.now()))
                .withConverter(toReverseDate::apply, toDate::apply, "Ошибка преобразования даты")
                .bind(FilmDto::getDateReleased, FilmDto::setDateReleased);

        binder.forField(directors).bind(FilmDto::getDirectors, FilmDto::setDirectors);
        binder.forField(actors).bind(FilmDto::getActors, FilmDto::setActors);

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
    protected void prepareForEditing(FilmDto entity) {
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
            var film = FilmDto.builder().build();
            binder.writeBeanIfValid(film);
            raiseApplyEvent(film);
        }
    }

    private static String getLabel(String fieldName) {
        return AnnotationProcessor.getTranslation(FilmDto.class, fieldName);
    }

    private void addToBinder(AbstractField<?, ?> field) {
        field.addValueChangeListener(e -> binder.validate());
    }

    private final Binder<FilmDto> binder = new Binder<>();

    private final IntegerField id = new IntegerField();
    private final TextField title = new TextField(getLabel("title"));
    private final DatePicker dateReleased = new DatePicker(getLabel("dateReleased"));
    private final TextField genre = new TextField(getLabel("genre"));
    private final TextField country = new TextField(getLabel("country"));
    private final TextArea description = new TextArea(getLabel("description"));
    private final IntegerField timeDuration = new IntegerField(getLabel("timeDuration"));
    private final MultiSelectComboBox<DirectorDto> directors = new MultiSelectComboBox<>(getLabel("directors"));
    private final MultiSelectComboBox<ActorDto> actors = new MultiSelectComboBox<>(getLabel("actors"));

    private FilmDto tmpEntity;
    private final Function<Date, LocalDate> toDate;
    private final Function<LocalDate, Date> toReverseDate;
    private final BigService service;
}
