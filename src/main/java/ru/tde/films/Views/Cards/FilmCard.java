package ru.tde.films.Views.Cards;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.*;
import ru.tde.films.Services.FilmService;
import ru.tde.films.Views.MainView;
import ru.tde.films.Views.Modals.CommentModal;
import ru.tde.films.Views.Util.CardBase;

import java.text.SimpleDateFormat;

@Scope("prototype")
@Component
@Route(value = "film/:filmId?", layout = MainView.class)
public class FilmCard extends CardBase<Film> {

    @Override
    public void setParameter(BeforeEvent event, Integer parameter) {
        entity = service.getById(parameter);
        super.setParameter(event, parameter);
    }

    protected VerticalLayout configureLayout() {
        var result = new VerticalLayout();
        result.setSpacing(false);
        result.setPadding(false);
        result.setMargin(false);

        var innerWrapper = new HorizontalLayout();
        innerWrapper.setPadding(false);
        innerWrapper.setSpacing(true);

        innerWrapper.add(configureFilmBlock(), configureCommentsBlock());
        result.add(innerWrapper);
        return result;
    }

    private VerticalLayout configureFilmBlock() {
        var filmBlock = new VerticalLayout();
        filmBlock.setPadding(false);
        filmBlock.setSpacing(false);
        filmBlock.setMargin(false);

        var actorsBlock = new Details("Актеры:");
        entity.getActors()
                .stream()
                .map(actor -> new RouterLink(actor.getFio(), ActorCard.class, actor.getId()))
                .forEach(actorsBlock::add);

        var directorsBlock = new Details("Режиссеры:");
        entity.getDirectors()
                .stream()
                .map(director -> new RouterLink(director.getFio(), DirectorCard.class, director.getId()))
                .forEach(directorsBlock::add);

        var title = new H1(entity.getTitle());
        var released = new Paragraph("Дата выпуска: " + dateFormatter.format(entity.getDateReleased()));
        var genre = new Paragraph("Жанр: " + entity.getGenre());
        var country = new Paragraph("Страна: " + entity.getCountry());
        var description = new Paragraph("Описание: " + entity.getDescription());
        var duration = new Paragraph("Продолжительность: " + entity.getTimeDuration().toHours() + " ч " + entity.getTimeDuration().toMinutesPart() + " мин");
        var editButton = new Button("Изменить", e -> modal.openWithParameters(entity, true));

        filmBlock.add(title, released, genre, country, duration, description, editButton);
        return filmBlock;
    }

    private VerticalLayout configureCommentsBlock() {
        var itemsBlock = new VerticalLayout();
        itemsBlock.setPadding(false);
        entity.getComments()
                .forEach(com -> itemsBlock.add(commentCardFabric(com)));

        var commentEntity = new Comment();
        commentEntity.setFilm(entity);
        commentEntity.setName("");
        commentEntity.setScore(0);
        commentEntity.setText("");

        var result = new VerticalLayout(
                new H2("Отзывы"),
                new Button("Оставить отзыв", e -> commentModal.openWithParameters(commentEntity, false)),
                itemsBlock
        );
        result.setAlignItems(Alignment.CENTER);
        return result;
    }

    @Autowired
    private FilmService service;

    @Autowired
    private CommentModal commentModal;

    @Autowired
    private SimpleDateFormat dateFormatter;

    private VerticalLayout commentCardFabric(Comment comment) {
        var res = new VerticalLayout();
        res.setSpacing(false);
        res.setPadding(false);
        res.setMargin(false);
        res.add(
                new H5("Псевдоним: " + comment.getName()),
                new H6("Оценка (0/10): " + comment.getScore()),
                new H6("Дата: " + dateFormatter.format(comment.getDateWritten())),
                new Text(comment.getText()),
                new Button("Редактировать", e -> commentModal.openWithParameters(comment, true))
        );

        return res;
    }
}