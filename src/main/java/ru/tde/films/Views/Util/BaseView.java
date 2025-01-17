package ru.tde.films.Views.Util;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Repositories.Specifications.BaseSpecification;

import java.util.*;

@Scope("prototype")
@Component
public abstract class BaseView<T, T2 extends FilterViewBase<T>, T3 extends ModalBase<T>> extends VerticalLayout {
    public BaseView(T2 filterView, T3 modal, PaginationView paginationView) {
        this.filterView = filterView;
        this.modal = modal;
        this.paginationView = paginationView;
        this.paginationView.addPageChangeListener(this::onNeedUpdateList);
        paginationView.setAlignSelf(Alignment.CENTER);
        this.filterView.addSearchListener(this::onNeedUpdateList);

        filterView.setSizeUndefined();
        filterView.setPadding(false);
        filterView.setSpacing(false);

        this.setAlignItems(Alignment.CENTER);

        var layout = new HorizontalLayout();
        layout.setAlignSelf(Alignment.START);
        layout.setSizeFull();
        layout.setAlignItems(Alignment.START);

        layout.getStyle().set("display", "flex");
        layout.getElement().getStyle().set("justify-content", "space-between");

        var itemsWithPaginationColumn = new VerticalLayout(paginationView, itemsList);
        itemsWithPaginationColumn.setSizeUndefined();
        itemsWithPaginationColumn.setAlignItems(Alignment.CENTER);

        var wrapper = new HorizontalLayout(itemsWithPaginationColumn);
        wrapper.setAlignItems(Alignment.CENTER);

        wrapper.setWidth("100%");
        wrapper.getStyle().set("display", "flex");
        wrapper.getStyle().set("justify-content", "center");
        var leftVerticalWrapper = new VerticalLayout(
                new Button("Добавить", e -> modal.openWithParameters(null, false)),
                filterView
        );

        leftVerticalWrapper.setPadding(false);
        leftVerticalWrapper.setSizeUndefined();
        layout.add(
                leftVerticalWrapper,
                wrapper
        );
        setAlignItems(Alignment.START);
        add(title, layout);
    }

    protected abstract List<T> getEntities(int page, int pageSize, BaseSpecification<T> specification);
    protected abstract VerticalLayout listCardFabric(T entity);

    protected void forceUpdateList() { onNeedUpdateList(); }

    protected void setMaxPage(int count) { paginationView.setPagesCount(count); }
    protected int getPageSize() { return paginationView.getPageSize(); }
    protected void setPageSize(int size) { paginationView.setPageSize(size);}
    protected void setTitle(String text) { title.setText(text); }

    private H1 title = new H1("TITLE");
    private final VerticalLayout itemsList = new VerticalLayout();
    private final PaginationView paginationView;
    private final T2 filterView;
    private final ModalBase<T> modal;

    private void updateList(List<T> entities) {
        itemsList.removeAll();
        for (var elem : entities)
            itemsList.add(listCardFabric(elem));
    }

    private void onNeedUpdateList() {
        var spec = filterView.getSpecification();
        var currentPage = paginationView.getCurrentPage();
        var pageSize = paginationView.getPageSize();

        var entities = getEntities(currentPage, pageSize, spec);
        updateList(entities);
    }
}
