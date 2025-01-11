package ru.tde.films.Services;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.tde.films.Repositories.*;
import ru.tde.films.Repositories.Specifications.FilmSpecification;
import ru.tde.films.Domain.Film;
import ru.tde.films.Repositories.Specifications.SearchCriteria;

@Service
public class FilmService {

    @Autowired
    private FilmRepository repo;

    public List<Film> getPagination(int page, int size) {
        return repo.findAll(PageRequest.of(page, size)).toList();
    }

    public List<Film> getFilteredPagination(List<FilmSpecification> filter, int page, int size) {
        Specification<Film> resultFilter = filter.get(0);
        for (int i = 1; i < filter.size(); i++)
            resultFilter = Specification.where(resultFilter).and(filter.get(i));

        return repo.findAll(resultFilter, PageRequest.of(page, size)).toList();
    }

    public Film getById(int id) {
        return repo.findOne(new FilmSpecification(new SearchCriteria("id", "=", String.valueOf(id)))).get();
    }
}
