package ru.tde.films.Views.Modals;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Repositories.Dto.CommentDto;
import ru.tde.films.Services.BigService;
import ru.tde.films.Views.Util.Annotation.AnnotationProcessor;
import ru.tde.films.Views.Util.ModalView;

import java.time.Instant;
import java.util.Date;


@Scope("prototype")
@Component
public class CommentModal extends ModalView<CommentDto> {
    @Autowired
    public CommentModal(BigService service) {
        super("Новый комментарий");
        this.service = service;
        init();
    }

    protected void init() {
        var form = new FormLayout();

        binder.forField(name).asRequired("Обязательное поле").bind(CommentDto::getName, CommentDto::setName);
        binder.forField(score)
                .asRequired("Обязательное поле")
                .withValidator(new IntegerRangeValidator("Оценка должна быть от 0 до 10", 0, 10))
                .bind(CommentDto::getScore, CommentDto::setScore);
        binder.forField(text).asRequired("Обязательное поле").bind(CommentDto::getText, CommentDto::setText);


        addToBinder(name);
        addToBinder(score);
        addToBinder(text);

        form.add(name, score, text);
        super.init(form);
    }

    public void openCreate(Integer filmId) {
        this.filmId = filmId;
        super.openCreate();
    }

    @Override
    protected void prepareForEditing(CommentDto entity) {
        throw new NotImplementedException();
    }

    @Override
    protected void prepareForCreating() {

    }

    @Override
    protected void applyButtonClick() {
        if (binder.isValid()) {
            var comment = CommentDto.builder()
                    .dateWritten(Date.from(Instant.now()))
                    .build();
            binder.writeBeanIfValid(comment);
            service.addComment(filmId, comment);
            close();
            raiseApplyEvent(comment);
        }
    }

    @Override
    protected void deleteButtonClick() {
        throw new NotImplementedException();
    }

    private static String getLabel(String fieldName) {
        return AnnotationProcessor.getTranslation(CommentDto.class, fieldName);
    }

    private void addToBinder(AbstractField<?, ?> field) {
        field.addValueChangeListener(e -> binder.validate());
    }

    private final Binder<CommentDto> binder = new Binder<>();
    private Integer filmId;
    private final BigService service;
    private final TextField name = new TextField(getLabel("name"));
    private final IntegerField score = new IntegerField(getLabel("score"));
    private final TextField text = new TextField(getLabel("text"));
}
