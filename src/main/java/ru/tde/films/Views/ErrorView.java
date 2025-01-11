package ru.tde.films.Views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

@Route("error")
public class ErrorView extends VerticalLayout implements HasErrorParameter<Exception> {
    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<Exception> parameter) {
        add(
                new H1("Страница не найдена!!"),
                new Text("Ошибка: " + parameter.getException().getMessage()),
                new Text(Arrays.stream(parameter.getException().getStackTrace()).map(StackTraceElement::toString).reduce((a, b) -> a + "\n\n" + b).toString())
        );
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }
}
