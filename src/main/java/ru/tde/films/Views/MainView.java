package ru.tde.films.Views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLayout;


public class MainView extends AppLayout implements RouterLayout {

    public MainView() {
        createMenu();
    }

    private void createMenu() {
        var b1 = makeNavButton("Главная", HomeView.class);
        var b2 = makeNavButton("Фильмы", FilmsView.class);
        var b3 = makeNavButton("Актеры", ActorsView.class);
        var b4 =  makeNavButton("Режиссеры", DirectorsView.class);
        var menuLayout = new HorizontalLayout(b1, b2, b3, b4);
        menuLayout.setSpacing(true);
        menuLayout.setAlignSelf(FlexComponent.Alignment.CENTER);
        menuLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        menuLayout.setFlexGrow(1, b1);
        menuLayout.setFlexGrow(1, b2);
        menuLayout.setFlexGrow(1, b3);
        menuLayout.setFlexGrow(1, b4);
        addToNavbar(menuLayout);
    }

    private <T extends Component> Button makeNavButton(String text, Class<T> target) {
        return new Button(text, e -> getUI().ifPresent(ui -> ui.navigate(target)));
    }
}

