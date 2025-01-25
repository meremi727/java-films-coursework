package ru.tde.films.Views;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.tde.films.Services.BigService;
import ru.tde.films.Views.Util.LayoutHelper;


@Route(value = "", layout = MainView.class)
@Scope("prototype")
@Component
public class HomeView extends VerticalLayout {
    @Autowired
    public HomeView(BigService service) {
        this.service = service;
        setAlignItems(Alignment.CENTER);

        var left = LayoutHelper.getCleanHorizontal();
        left.setSpacing(true);
        left.setAlignItems(Alignment.CENTER);
        left.setAlignSelf(Alignment.CENTER);
        left.add(createCountryChart(), createCommentsChart());
        add(left);
        var right = LayoutHelper.getCleanHorizontal();
        right.setSpacing(true);
        right.add(createGenreChart());
        add(left, right);
    }

    private Chart createCommentsChart() {
        var data = service.getCommentsScoresCount();

        var scoreChart = new Chart(ChartType.COLUMN);
        var conf = scoreChart.getConfiguration();
        conf.setTitle("Оценки пользователей");
        var series = new DataSeries("Оценки(0-10)");
        for (int i = 0; i <= 10; i++) {
            var item = new DataSeriesItem(String.valueOf(i), data.getOrDefault(i, 0L));
            series.add(item);
        }
        conf.addSeries(series);
        return scoreChart;
    }

    private Chart createGenreChart() {
        var data = service.getGenreCount();

        var genreChart = new Chart(ChartType.PIE);
        var conf = genreChart.getConfiguration();
        conf.setTitle("Жанры фильмов");
        var series = new DataSeries("Жанры");
        data.entrySet()
                .stream()
                .map(p -> new DataSeriesItem(p.getKey(), p.getValue()))
                .forEach(series::add);

        conf.addSeries(series);
        return genreChart;
    }

    private Chart createCountryChart() {
        var data = service.getActorsCountryCount();

        var countryChart = new Chart(ChartType.COLUMN);
        var conf = countryChart.getConfiguration();
        conf.setTitle("Количество актеров по странам");
        data.entrySet()
                .stream()
                .map(p -> {
                    var ds = new DataSeries(p.getKey());
                    ds.add(new DataSeriesItem(p.getKey(), p.getValue()));
                    return ds;
                })
                .forEach(conf::addSeries);


        return countryChart;
    }

    private final BigService service;
}

