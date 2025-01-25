package ru.tde.films;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.*;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.function.Function;

@Configuration
public class AppConfiguration {

    @Bean
    public SimpleDateFormat getDateFormatter() {
        return new SimpleDateFormat("d MMMM yyyy");
    }

    @Bean
    public Function<Date, LocalDate> getConverter() {
        return (Date date) -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Bean
    public Function<LocalDate, Date> getReverseDateConverter() {
        return (LocalDate date) -> Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Bean
    public ModelMapper getModelMapper() {
        return MapperConfiguration.getMapper();
    }
}
