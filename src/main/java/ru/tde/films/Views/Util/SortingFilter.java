package ru.tde.films.Views.Util;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.data.domain.Sort;

import java.util.*;


public class SortingFilter<T> extends VerticalLayout {

    private final Map<String, String> map = new HashMap<>();

    private final VerticalLayout list = new VerticalLayout();

    public Sort getOrder() {
        var sort = Sort.unsorted();

        for (var elem : list.getChildren().toList()) {
            var node = (NodeView) elem;
            var realField = map.get(node.field);
            sort = sort.and(Sort.by(node.order, realField));
        }
        return sort;
    }

    public SortingFilter(Map<String, String> sortingFields) {
        setPadding(false);
        for (var entry : sortingFields.entrySet())
            map.put(entry.getValue(), entry.getKey());

        var cb = new ComboBox<String>("Порядок сортировки:");
        cb.setItems(sortingFields.values());
        cb.addValueChangeListener(e -> {
            var selectedValue = e.getValue();
            if (selectedValue != null && !selectedValue.isBlank() && list.getChildren().map(i -> (NodeView)i).noneMatch(i -> i.field.equals(selectedValue))) {
                addNode(selectedValue);
                cb.setValue("");
            }
        });

        var clearBtn = new Button("Очистить", e -> clearList());
        setAlignItems(Alignment.STRETCH);
        list.setPadding(false);
        add(
                cb,
                list,
                clearBtn
        );
    }

    private void clearList() {
        list.removeAll();
    }

    private void addNode(String field) {
        var n = new NodeView(field, Sort.Direction.ASC);
        list.add(n);
    }

    private void removeNode(NodeView view) {
        list.remove(view);
    }

    private class NodeView extends HorizontalLayout {
        private final String UP_ARROW = "↑";
        private final String DOWN_ARROW = "↓";
        private final Button orderBtn = new Button();

        private Sort.Direction order;
        private final String field;

        public NodeView(String field, Sort.Direction order) {
            this.field = field;
            this.order = order;
            orderBtn.setText(order == Sort.Direction.ASC ? DOWN_ARROW : UP_ARROW);
            orderBtn.addClickListener(e -> changeOrder());
            Button deleteBtn = new Button("-", e -> removeNode(this));

            add(
                    new Text(field),
                    new HorizontalLayout(orderBtn, deleteBtn)
            );

            setSizeFull();

            getElement().getStyle().set("display", "flex");
            getElement().getStyle().set("justify-content", "space-between");
            getElement().getStyle().set("margin-right", "auto");
        }

        private void changeOrder() {
            order = order == Sort.Direction.ASC ? Sort.Direction.DESC : Sort.Direction.ASC;
            orderBtn.setText(order == Sort.Direction.ASC ? DOWN_ARROW : UP_ARROW);
        }
    }
}
