package ru.tde.films.Views.Cards;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Views.MainView;

@Scope("prototype")
@Component
@Route(value = "director/:directorId?", layout = MainView.class)
public class DirectorCard extends VerticalLayout implements HasUrlParameter<Integer> {

    @Override
    public void setParameter(BeforeEvent event, Integer parameter) {

    }
}
