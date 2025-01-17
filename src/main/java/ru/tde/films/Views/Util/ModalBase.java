package ru.tde.films.Views.Util;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.apache.commons.lang3.NotImplementedException;
import ru.tde.films.Views.HomeView;

public abstract class ModalBase<T> extends Dialog {
    private boolean hasDeleteAction;
    private T entity;
    private FormLayout form;

    public void openWithParameters(T entity, boolean hasDeleteAction) {
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);

        this.entity = entity;
        this.hasDeleteAction = hasDeleteAction;
        setModal(true);
        form = this.setupFields(entity);
        wrapForm();
        open();
    }

    protected abstract FormLayout setupFields(T entity);

    protected abstract void apply(FormLayout form, T entity);

    protected abstract void discardChanges(FormLayout form, T entity);

    protected void delete(FormLayout form, T entity) { throw new NotImplementedException(); }

    private void wrapForm() {
        this.removeAll();
        var hl = new HorizontalLayout(
                new Button("Применить", this::applyHandler),
                new Button("Сбросить", this::discardChangesHandler),
                new Button("Закрыть", e -> close())
        );

        if (hasDeleteAction)
            hl.add(new Button("Удалить", this::deleteHandler));
        form.add(hl);
        add(form);
    }

    private void deleteHandler(ClickEvent<Button> buttonClickEvent) {
        var dialog = new Dialog("Внимание!");
        dialog.add(
                new VerticalLayout(
                    new Text("Вы подтверждаете операцию?"),
                    new HorizontalLayout(
                        new Button("Да", e -> {
                            delete(form, entity);
                            dialog.close();
                            close();
                        }),
                        new Button("Нет", e -> dialog.close())
                    )
                )
        );
        dialog.open();
    }

    private void discardChangesHandler(ClickEvent<Button> e) { discardChanges(form, entity); }

    private void applyHandler(ClickEvent<Button> e) { apply(form, entity); close(); }
}
