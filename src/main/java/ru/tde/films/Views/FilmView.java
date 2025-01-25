package ru.tde.films.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Film;
import ru.tde.films.Repositories.Dto.CommentDto;
import ru.tde.films.Repositories.Specifications.BaseSpecification;
import ru.tde.films.Services.BigService;
import ru.tde.films.Repositories.Dto.FilmDto;
import ru.tde.films.Views.Filters.FilmFilter;
import ru.tde.films.Views.Modals.CommentModal;
import ru.tde.films.Views.Modals.FilmModal;
import ru.tde.films.Views.Sorts.FilmSort;
import ru.tde.films.Views.Util.*;

import java.text.SimpleDateFormat;
import java.util.*;


@Scope("prototype")
@Component
@Route(value = "films", layout = MainView.class)
public class FilmView extends View<Film, FilmDto> {
    @Autowired
    public FilmView(FilmSort sortView, FilmFilter filterView, FilmModal modalView, CommentModal commentModal, BigService service, SimpleDateFormat dateFormatter) {
        super(sortView, filterView, modalView);
        this.service = service;
        this.dateFormatter = dateFormatter;
        this.commentModal = commentModal;
        commentModal.addApplyChangesEventListener(e -> initForceUpdate());
        initForceUpdate();
    }

    @Override
    protected void deleteEntity(FilmDto entity) {
        service.deleteFilm(entity);
    }

    @Override
    protected void saveEntity(FilmDto entity) {
        service.saveFilm(entity);
    }

    @Override
    protected CardView<FilmDto> cardFabric(FilmDto entity) {
        var card = new FilmCard();
        card.init(entity);
        return card;
    }

    @Override
    protected List<FilmDto> updateData(BaseSpecification<Film, FilmDto> specification) {
        return service.getFilteredFilms(specification);
    }

    private final BigService service;
    private final SimpleDateFormat dateFormatter;
    private final CommentModal commentModal;


    private class FilmCard extends VerticalLayout implements CardView<FilmDto> {

        @Override
        public void init(FilmDto entity) {
            add(new H3(entity.getTitle()));

            addLabels(
                    "Дата выхода: " + dateFormatter.format(entity.getDateReleased()),
                    "Жанр: " + entity.getGenre(),
                    "Страна: " + entity.getCountry(),
                    "Продолжительность: " + entity.getTimeDuration()
            );

            var directors = new Details("Режиссеры");
            entity.getDirectors()
                    .stream()
                    .map(e -> new Paragraph(e.getFio()))
                    .forEach(directors::add);

            var actors = new Details("Актеры");
            entity.getActors()
                    .stream()
                    .map(e -> new Paragraph(e.getFio()))
                    .forEach(actors::add);

            var desc = new Details("Описание");
            desc.add(entity.getDescription());

            var editButton = new Button("✏\uFE0F", event -> openEditModal(entity));

            var comments = new Details("Комментарии");
            comments.add(new Button("Оставить комментарий", e -> commentModal.openCreate(entity.getId())));
            entity.getComments()
                    .stream()
                    .map(this::fabric)
                    .forEach(comments::add);

            add(directors, actors, desc, editButton, comments);
            setPadding(true);
            setSpacing(false);
        }

        private void addLabels(String... labels) {
            for (var label : labels)
                add(new Label(label));
        }

        private CommentCard fabric(CommentDto dto) {
            var card = new CommentCard();
            card.init(dto);
            return card;
        }

        @Override
        public com.vaadin.flow.component.Component toComponent() {
            return this;
        }
    }


    private class CommentCard extends VerticalLayout implements CardView<CommentDto> {

        @Override
        public void init(CommentDto entity) {
            add(new H3(entity.getName()));

            addLabels(
                    "Оценка: " + entity.getScore(),
                    "Дата: " + dateFormatter.format(entity.getDateWritten())
            );
            var text = new TextArea("Отзыв");
            text.setValue(entity.getText());
            text.setReadOnly(true);
            add(text);
            setPadding(true);
            setSpacing(false);
        }

        private void addLabels(String... labels) {
            for (var label : labels)
                add(new Label(label));
        }

        @Override
        public com.vaadin.flow.component.Component toComponent() {
            return this;
        }
    }
}
