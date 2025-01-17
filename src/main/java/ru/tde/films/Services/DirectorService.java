package ru.tde.films.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.tde.films.Domain.Director;
import ru.tde.films.Repositories.DirectorRepository;
import ru.tde.films.Repositories.Specifications.DirectorSpecification;

import java.util.List;

@Service
public class DirectorService {
    @Autowired
    private DirectorRepository repo;

    public List<Director> getAll() { return repo.findAll(); }

    public List<Director> getPagination(int page, int size) {
        return repo.findAll(PageRequest.of(page, size)).toList();
    }

    public List<Director> getFilteredPagination(DirectorSpecification filter, int page, int size) {
        var spec = filter.buildSpecification();
        var sort = filter.buildSort();
        return repo.findAll(spec, PageRequest.of(page, size, sort)).toList();
    }

    public Director getById(int id) {
        return repo.findOne(new DirectorSpecification()
                .hasId(id)
                .buildSpecification()
        ).orElse(null);
    }

    public void saveDirector(Director director) {
        repo.save(director);
    }

    public void deleteDirector(Director director) { repo.delete(director); }
}
