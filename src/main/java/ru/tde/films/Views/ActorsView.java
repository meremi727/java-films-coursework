package ru.tde.films.Views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "actors", layout = MainView.class)
public class ActorsView extends VerticalLayout {
    public ActorsView() {
        add(new H1("Список актеров"));
    }
}
