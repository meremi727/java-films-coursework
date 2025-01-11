package ru.tde.films.Views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainView.class)
public class HomeView extends VerticalLayout {
    public HomeView() {
        add(new H1("Добро пожаловать на главную страницу!"));
    }
}
