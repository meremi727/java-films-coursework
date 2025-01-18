package ru.tde.films.Views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;

public class MainView extends AppLayout implements RouterLayout {

    public MainView() {
        createMenu();
    }

    private void createMenu() {
//        var b1 = makeNavButton("Главная", HomeView.class);
        var b2 = makeNavButton("Фильмы", FilmView.class);
        var b3 = makeNavButton("Актеры", ActorView.class);
        var b4 = makeNavButton("Режиссеры", DirectorView.class);

//        var menuLayout = new HorizontalLayout(b1, b2, b3, b4);
        var menuLayout = new HorizontalLayout(b2, b3, b4);
        menuLayout.setSpacing(true);

        menuLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

//        menuLayout.setFlexGrow(1, b1);
        menuLayout.setFlexGrow(1, b2);
        menuLayout.setFlexGrow(1, b3);
        menuLayout.setFlexGrow(1, b4);

        var verticalLayout = new VerticalLayout(menuLayout);
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        addToNavbar(verticalLayout);
    }

    private <T extends Component> Button makeNavButton(String text, Class<T> target) {
        return new Button(text, e -> getUI().ifPresent(ui -> ui.navigate(target)));
    }
}
