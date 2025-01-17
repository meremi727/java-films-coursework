package ru.tde.films.Views.Modals;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Domain.Comment;
import ru.tde.films.Services.FilmService;
import ru.tde.films.Views.Cards.FilmCard;
import ru.tde.films.Views.Util.ModalBase;

import java.time.Instant;
import java.util.Date;

@Scope("prototype")
@Component
public class CommentModal extends ModalBase<Comment> {

    @Autowired
    private FilmService filmService;

    private TextField nameField;
    private NumberField scoreField;
    private TextArea textArea;

    public CommentModal() { setHeaderTitle("Комментарий"); }

    @Override
    protected FormLayout setupFields(Comment comment) {
        var form = createForm();
        return fillFormValues(form, comment);
    }

    @Override
    protected void apply(FormLayout form, Comment comment) {
        assert comment != null && comment.getFilm() != null;

        comment.setName(nameField.getValue());
        comment.setScore(scoreField.getValue().intValue());
        comment.setText(textArea.getValue());
        comment.setDateWritten(Date.from(Instant.now()));

        filmService.addComment(comment.getFilm(), comment);
        UI.getCurrent().navigate(FilmCard.class, comment.getFilm().getId());
    }

    @Override
    protected void discardChanges(FormLayout form, Comment comment) { fillFormValues(form, comment); }

    @Override
    protected void delete(FormLayout form, Comment comment) {
        assert comment != null && comment.getFilm() != null;
        int id = comment.getFilm().getId();
        filmService.removeComment(comment.getFilm(), comment);

        UI.getCurrent().navigate(FilmCard.class, id);
    }

    private FormLayout createForm() {
        var form = new FormLayout();

        nameField = new TextField("Псевдоним");
        nameField.setRequired(true);

        scoreField = new NumberField("Оценка (0/10)");
        scoreField.setRequired(true);
        scoreField.setMin(0);
        scoreField.setMax(10);
        scoreField.setStep(1);

        this.textArea = new TextArea("Комментарий");

        form.add(
                nameField,
                scoreField,
                textArea
        );

        form.setColspan(textArea, 2);

        return form;
    }

    private FormLayout fillFormValues(FormLayout form, Comment comment) {
        if (comment == null) {
            nameField.clear();
            scoreField.clear();
            textArea.clear();
        }
        else {
            nameField.setValue(comment.getName());
            scoreField.setValue((double)comment.getScore());
            textArea.setValue(comment.getText());
        }
        return form;
    }
}
