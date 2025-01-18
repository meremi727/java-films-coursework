package ru.tde.films.Services;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import ru.tde.films.Repositories.*;
import ru.tde.films.Domain.Film;
import ru.tde.films.Repositories.Specifications.*;

@Service
public class FilmService {
    public List<Film> getAll() { return filmRepo.findAll(); }
    
    public List<Film> getFiltered(BaseSpecification<Film> filter) {
        var spec = filter.buildSpecification();
        var sort = filter.buildSort();
        return filmRepo.findAll(spec, sort);
    }

    public Film getById(int id) {
        return filmRepo.findOne(new FilmSpecification()
                .hasId(id)
                .buildSpecification()
                ).orElse(null);
    }

    public void saveFilm(Film film) {
        filmRepo.save(film);
    }

    public void deleteFilm(Film film) { filmRepo.delete(film); }

    @Autowired
    private FilmRepository filmRepo;

    @Autowired
    private ActorRepository actorRepo;

    @Autowired
    private DirectorRepository directorRepo;
}
