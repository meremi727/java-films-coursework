package ru.tde.films.Views.Modals;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.DateRangeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Director;
import ru.tde.films.Domain.Film;
import ru.tde.films.Domain.Gender;
import ru.tde.films.Services.FilmService;
import ru.tde.films.Views.Util.Annotation.AnnotationProcessor;
import ru.tde.films.Views.Util.ModalView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Scope("prototype")
@Component
public class DirectorModal extends ModalView<Director> {
    @Autowired
    public DirectorModal(FilmService filmService) {
        super("Режиссер");
        this.filmService = filmService;
        init();
    }

    protected void init() {
        var form = new FormLayout();

        id.setVisible(false);
        id.setEnabled(false);
        addToBinder(id);

        gender.setItems(Gender.values());
        gender.setItemLabelGenerator(Gender::toString);

        films.setItems(filmService.getAll());
        films.setItemLabelGenerator(Film::getTitle);

        binder.forField(id).bind(Director::getId, Director::setId);
        binder.forField(gender).asRequired("Обязательное поле").bind(Director::getGender, Director::setGender);
        binder.forField(country).asRequired("Обязательное поле").bind(Director::getCountry, Director::setCountry);
        binder.forField(surname).asRequired("Обязательное поле").bind(Director::getSurname, Director::setSurname);
        binder.forField(name).asRequired("Обязательное поле").bind(Director::getName, Director::setName);
        binder.forField(patronymic).asRequired("Обязательное поле").bind(Director::getPatronymic, Director::setPatronymic);
        binder.forField(dateOfBirth)
                .asRequired("Обязательное поле")
                .withValidator(new DateRangeValidator("Неверная дата", LocalDate.MIN, LocalDate.now()))
                .withConverter(DirectorModal::fromLocalDate, DirectorModal::fromDate, "Ошибка преобразования даты")
                .bind(Director::getDateOfBirth, Director::setDateOfBirth);
        binder.forField(dignity).asRequired("Обязательное поле").bind(Director::getDignity, Director::setDignity);
        binder.forField(films).bind(Director::getFilms, Director::setFilms);

        addToBinder(gender);
        addToBinder(country);
        addToBinder(surname);
        addToBinder(name);
        addToBinder(patronymic);
        addToBinder(dateOfBirth);
        addToBinder(dignity);
        addToBinder(films);

        form.add(gender, country, surname, name, patronymic, dateOfBirth, dignity, films);
        super.init(form);

    }

    @Override
    protected void prepareForEditing(Director entity) {
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
            var director = new Director();
            binder.writeBeanIfValid(director);
            raiseApplyEvent(director);
        }
    }

    private static String getLabel(String fieldName) { return AnnotationProcessor.getTranslation(Director.class, fieldName);}

    private static Date fromLocalDate(LocalDate date) { return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()); }

    private static LocalDate fromDate(Date date) { return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); }

    private void addToBinder(AbstractField<?, ?> field) { field.addValueChangeListener(e -> binder.validate()); }

    private Director tmpEntity;
    private final FilmService filmService;
    private final Binder<Director> binder = new Binder<>();

    private final IntegerField id = new IntegerField();
    private final ComboBox<Gender> gender = new ComboBox<>(getLabel("gender"));
    private final TextField country = new TextField(getLabel("country"));
    private final TextField surname = new TextField(getLabel("surname"));
    private final TextField name = new TextField(getLabel("name"));
    private final TextField patronymic = new TextField(getLabel("patronymic"));
    private final DatePicker dateOfBirth = new DatePicker(getLabel("dateOfBirth"));
    private final TextField dignity = new TextField(getLabel("dignity"));
    private final MultiSelectComboBox<Film> films = new MultiSelectComboBox<>(getLabel("films"));

}
