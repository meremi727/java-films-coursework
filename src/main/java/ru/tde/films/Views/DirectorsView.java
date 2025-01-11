package ru.tde.films.Views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "directors", layout = MainView.class)
public class DirectorsView extends VerticalLayout {
    public DirectorsView() {
        add(new H1("Список режиссеров"));
    }
}
