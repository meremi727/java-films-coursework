package ru.tde.films.Views.Util;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

import java.util.function.Consumer;
import java.util.*;


public abstract class ModalView<T> extends Dialog {

    public void addApplyChangesEventListener(Consumer<T> consumer) { applyHandlers.add(consumer); }

    public void addDeleteEventListener(Consumer<T> consumer) { deleteHandlers.add(consumer); }

    public ModalView(String title) { super(title); }

    public void openEdit(T entity) {
        prepareForEditing(entity);
        deleteBtn.setVisible(true);
        open();
    }

    public void openCreate() {
        prepareForCreating();
        deleteBtn.setVisible(false);
        open();
    }

    protected void init(FormLayout form) {
        var container = LayoutHelper.getCleanVertical();
        container.setSpacing(true);
        var applyBtn = new Button("Сохранить", e -> applyButtonClick());
        var closeBtn = new Button("Закрыть", e -> close());

        var buttonsContainer = LayoutHelper.getCleanHorizontal();
        buttonsContainer.setSpacing(true);
        buttonsContainer.add(applyBtn, deleteBtn, closeBtn);
        buttonsContainer.setAlignSelf(FlexComponent.Alignment.CENTER);

        container.add(form, buttonsContainer);
        add(container);
    }

    protected void raiseApplyEvent(T entity) { applyHandlers.forEach(c -> c.accept(entity)); }

    protected void raiseDeleteEvent(T entity) { deleteHandlers.forEach(c -> c.accept(entity)); }

    protected abstract void prepareForEditing(T entity);
    protected abstract void prepareForCreating();
    protected abstract void deleteButtonClick();
    protected abstract void applyButtonClick();

    private void deleteHandler() {
        new ConfirmDialog(
                "Подтверждение удаления",
                "Вы уверены, что хотите удалить?",
                "Да",
                e -> deleteButtonClick(),
                "Нет",
                e -> e.getSource().close()).open();
    }

    private final Button deleteBtn = new Button("Удалить", e -> deleteHandler());
    private final List<Consumer<T>> applyHandlers = new ArrayList<>();
    private final List<Consumer<T>> deleteHandlers = new ArrayList<>();
}