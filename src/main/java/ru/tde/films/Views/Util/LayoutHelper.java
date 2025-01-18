package ru.tde.films.Views.Util;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class LayoutHelper {

    public static VerticalLayout getCleanVertical() {
        var container = new VerticalLayout();
        container.setMargin(false);
        container.setPadding(false);
        container.setSpacing(false);
        return container;
    }

    public static HorizontalLayout getCleanHorizontal() {
        var container = new HorizontalLayout();
        container.setMargin(false);
        container.setPadding(false);
        container.setSpacing(false);
        return container;
    }
}
