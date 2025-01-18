package ru.tde.films.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.tde.films.Domain.Director;
import ru.tde.films.Domain.Film;
import ru.tde.films.Repositories.DirectorRepository;
import ru.tde.films.Repositories.FilmRepository;
import ru.tde.films.Repositories.Specifications.BaseSpecification;
import ru.tde.films.Repositories.Specifications.DirectorSpecification;

import java.util.Arrays;
import java.util.List;

@Service
public class DirectorService {
    public List<Director> getAll() { return directorRepo.findAll(); }

    public List<Director> getFiltered(BaseSpecification<Director> filter) {
        var spec = filter.buildSpecification();
        var sort = filter.buildSort();
        return directorRepo.findAll(spec, sort);
    }

    public Director getById(int id) {
        return directorRepo.findOne(new DirectorSpecification()
                .hasId(id)
                .buildSpecification()
        ).orElse(null);
    }

    public void saveDirector(Director director) { directorRepo.save(director); }

    public void deleteDirector(Director director) { directorRepo.delete(director); }

    public void addFilms(Director director, Film... films) {
        Arrays.stream(films).forEach(f -> f.getDirectors().add(director));
        filmRepo.saveAll(Arrays.stream(films).toList());
    }

    public void removeFilms(Director director, Film... films) {
        Arrays.stream(films).forEach(f -> f.getDirectors().remove(director));
        filmRepo.saveAll(Arrays.stream(films).toList());
    }

    @Autowired
    private DirectorRepository directorRepo;

    @Autowired
    private FilmRepository filmRepo;
}
