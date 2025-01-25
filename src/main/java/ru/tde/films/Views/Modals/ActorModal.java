package ru.tde.films.Views.Modals;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.DateRangeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.*;
import ru.tde.films.Repositories.Dto.*;
import ru.tde.films.Views.Util.Annotation.AnnotationProcessor;
import ru.tde.films.Views.Util.ModalView;

import java.time.*;
import java.util.*;
import java.util.function.Function;

@Scope("prototype")
@Component
public class ActorModal extends ModalView<ActorDto> {
    @Autowired
    public ActorModal(Function<Date, LocalDate> f1, Function<LocalDate, Date> f2) {
        super("Актер");
        this.toDate = f1;
        this.toReverseDate = f2;
        init();
    }

    protected void init() {
        var form = new FormLayout();

        addToBinder(id);

        gender.setItems(Gender.values());
        gender.setItemLabelGenerator(Gender::toString);

        binder.forField(id).bind(ActorDto::getId, ActorDto::setId);
        binder.forField(gender).asRequired("Обязательное поле").bind(ActorDto::getGender, ActorDto::setGender);
        binder.forField(country).asRequired("Обязательное поле").bind(ActorDto::getCountry, ActorDto::setCountry);
        binder.forField(surname).asRequired("Обязательное поле").bind(ActorDto::getSurname, ActorDto::setSurname);
        binder.forField(name).asRequired("Обязательное поле").bind(ActorDto::getName, ActorDto::setName);
        binder.forField(patronymic).asRequired("Обязательное поле").bind(ActorDto::getPatronymic, ActorDto::setPatronymic);
        binder.forField(dateOfBirth)
                .asRequired("Обязательное поле")
                .withValidator(new DateRangeValidator("Неверная дата", LocalDate.MIN, LocalDate.now()))
                .withConverter(toReverseDate::apply, toDate::apply, "Ошибка преобразования даты")
                .bind(ActorDto::getDateOfBirth, ActorDto::setDateOfBirth);

        addToBinder(gender);
        addToBinder(country);
        addToBinder(surname);
        addToBinder(name);
        addToBinder(patronymic);
        addToBinder(dateOfBirth);

        form.add(gender, country, surname, name, patronymic, dateOfBirth);
        super.init(form);
    }

    @Override
    protected void prepareForEditing(ActorDto entity) {
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
            var actor = ActorDto.builder().build();
            binder.writeBeanIfValid(actor);
            raiseApplyEvent(actor);
        }
    }

    private static String getLabel(String fieldName) {
        return AnnotationProcessor.getTranslation(ActorDto.class, fieldName);
    }

    private void addToBinder(AbstractField<?, ?> field) {
        field.addValueChangeListener(e -> binder.validate());
    }


    private final Function<Date, LocalDate> toDate;
    private final Function<LocalDate, Date> toReverseDate;
    private ActorDto tmpEntity;
    private final Binder<ActorDto> binder = new Binder<>();
    private final ComboBox<Gender> gender = new ComboBox<>(getLabel("gender"));
    private final TextField country = new TextField(getLabel("country"));
    private final TextField surname = new TextField(getLabel("surname"));
    private final TextField name = new TextField(getLabel("name"));
    private final TextField patronymic = new TextField(getLabel("patronymic"));
    private final DatePicker dateOfBirth = new DatePicker(getLabel("dateOfBirth"));

    private final IntegerField id = new IntegerField();
}
