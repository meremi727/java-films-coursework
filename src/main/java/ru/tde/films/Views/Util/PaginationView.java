package ru.tde.films.Views.Util;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Scope("prototype")
@Component
public class PaginationView extends HorizontalLayout {
    public int getCurrentPage() { return currentPage - 1; }

    public int getPageSize() { return pageSize; }

    public int getPagesCount() { return pagesCount; }

    public void setPagesCount(int pagesCount) { this.pagesCount = pagesCount + 1; label.setText(currentPage + "/" + this.pagesCount);}

    public void setPageSize(int size) { this.pageSize = size; }

    public void addPageChangeListener(Runnable listener) { listeners.add(listener); }

    public PaginationView() {
        Button previousButton = new Button("←", event -> changePage(-1));
        Button nextButton = new Button("→", event -> changePage(1));

        add(previousButton, label, nextButton);
        setAlignItems(Alignment.BASELINE);
    }

    private void changePage(int delta) {
        currentPage += delta;
        if (currentPage < 1)
            currentPage = 1;

        if (currentPage > pagesCount)
            currentPage -= delta;

        label.setText(currentPage + "/" + pagesCount);
        firePageChangeEvent();
    }

    private void firePageChangeEvent() {
        for (var listener : listeners)
            if (listener != null)
                listener.run();
    }

    private int currentPage = 1;
    private int pageSize = 10;
    private int pagesCount = 1;
    private final Text label = new Text("1");
    private final List<Runnable> listeners = new ArrayList<>();
}
