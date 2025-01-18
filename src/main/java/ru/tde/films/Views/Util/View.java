package ru.tde.films.Views.Util;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Repositories.Specifications.BaseSpecification;
import java.util.*;


@Scope("prototype")
@Component
public abstract class View<T> extends HorizontalLayout {
    public View(SortView<T> sortView, FilterView<T> filterView, ModalView<T> modalView) {
        this.sortView = sortView;
        this.filterView = filterView;
        this.modalView = modalView;
        modalView.addApplyChangesEventListener(e -> { this.saveEntity(e); modalView.close(); applyFiltersAndSort(); });
        modalView.addDeleteEventListener(e -> { this.deleteEntity(e); modalView.close(); applyFiltersAndSort(); });
        initLayout();
    }

    protected void openEditModal(T entity) { modalView.openEdit(entity); }

    protected abstract void deleteEntity(T entity);
    protected abstract void saveEntity(T entity);
    protected abstract CardView<T> cardFabric(T entity);
    protected abstract List<T> updateData(BaseSpecification<T> specification);

    private void initLayout() {
        var leftBlock = new VerticalLayout();
        leftBlock.setSizeUndefined();

        var modalBtn = new Button("Добавить", e -> modalView.openCreate());
        var applyBtn = new Button("Показать", e -> applyFiltersAndSort());
        var clearBtn = new Button("Очистить", e -> { sortView.clear(); filterView.clear(); });

        var btnBlock = LayoutHelper.getCleanVertical();
        btnBlock.setAlignItems(Alignment.START);
        btnBlock.add(modalBtn, applyBtn, clearBtn);

        var inner = LayoutHelper.getCleanVertical();
        inner.add(new Details("Сортировка", sortView), new Details("Фильтры", filterView));

        leftBlock.add(btnBlock, inner);

        cardsBlock = new VerticalLayout();
        cardsBlock.setSizeUndefined();
        var wrapper = LayoutHelper.getCleanVertical();
        wrapper.setAlignItems(Alignment.CENTER);
        wrapper.setSizeFull();
        wrapper.add(cardsBlock);

        add(leftBlock, wrapper);
    }

    private void applyFiltersAndSort() {
        var items = updateData(filterView.getSpecification().withSort(sortView.getOrder()));
        cardsBlock.removeAll();
        items.stream()
                .map(this::cardFabric)
                .map(CardView::toComponent)
                .forEach(cardsBlock::add);
    }

    private VerticalLayout cardsBlock;
    private final SortView<T> sortView;
    private final FilterView<T> filterView;
    private final ModalView<T> modalView;
}
