package ru.tde.films.Views.Util;

import java.util.*;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.*;
import org.javatuples.Pair;
import org.springframework.data.domain.Sort;
import ru.tde.films.Views.Util.Annotation.AnnotationProcessor;


public abstract class SortView<T> extends VerticalLayout {
    public SortView(Class<T> _class, Set<String> sortingFields) {
        setPadding(false);

        var cb = new ComboBox<Pair<String, String>>("Порядок сортировки:");
        cb.setItems(sortingFields
                .stream()
                .map(e -> new Pair<String, String>(e, AnnotationProcessor.getTranslation(_class, e)))
                .toList()
        );

        cb.setItemLabelGenerator(Pair::getValue1);
        cb.addValueChangeListener(e -> {
            var selectedValue = e.getValue();
            if (selectedValue != null && list.getChildren().map(i -> (NodeView)i).noneMatch(n -> n.value.equals(selectedValue))) {
                addNode(selectedValue);
                cb.setValue(cb.getEmptyValue());
            }
        });

        setAlignItems(Alignment.STRETCH);
        list.setPadding(false);
        add(cb, list);
    }

    public void clear() { list.removeAll(); }

    public Sort getOrder() {
        var sort = Sort.unsorted();

        for (var elem : list.getChildren().map(e -> (NodeView)e).toList()) {
            var field = elem.value.getValue0();
            var order = elem.order;

            sort = sort.and(Sort.by(order, field));
        }
        return sort;
    }

    private void addNode(Pair<String, String> value) {
        var n = new NodeView(value, Sort.Direction.ASC);
        list.add(n);
    }

    private void removeNode(NodeView view) { list.remove(view); }

    private final VerticalLayout list = new VerticalLayout();

    private class NodeView extends HorizontalLayout {
        private final String UP_ARROW = "↑", DOWN_ARROW = "↓";

        private Sort.Direction order;
        private final Pair<String, String> value;

        public NodeView(Pair<String, String> value, Sort.Direction order) {
            this.value = value;
            this.order = order;

            var orderBtn = new Button(order == Sort.Direction.ASC ? DOWN_ARROW : UP_ARROW);
            orderBtn.addClickListener(e -> {
                this.order = this.order == Sort.Direction.ASC ? Sort.Direction.DESC : Sort.Direction.ASC;
                orderBtn.setText(this.order == Sort.Direction.ASC ? DOWN_ARROW : UP_ARROW);
                orderBtn.addThemeVariants(ButtonVariant.LUMO_ICON);
            });
            orderBtn.addThemeVariants(ButtonVariant.LUMO_ICON);

            var deleteBtn = new Button("-", e -> removeNode(this));
            deleteBtn.addThemeVariants(ButtonVariant.LUMO_ICON);

            add(
                    new Text(value.getValue1()),
                    new HorizontalLayout(orderBtn, deleteBtn)
            );

            setSizeFull();
            getElement().getStyle().set("display", "flex");
            getElement().getStyle().set("justify-content", "space-between");
            getElement().getStyle().set("margin-right", "auto");
        }
    }
}