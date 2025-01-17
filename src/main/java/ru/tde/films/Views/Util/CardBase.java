package ru.tde.films.Views.Util;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CardBase<T> extends VerticalLayout implements HasUrlParameter<Integer> {
    @Override
    public void setParameter(BeforeEvent event, Integer parameter) {
        removeAll();
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        var layout = configureLayout();
        layout.setSizeUndefined();

        add(layout);
    }

    protected T entity;

    @Autowired
    protected ModalBase<T> modal;

    protected abstract VerticalLayout configureLayout();
}
