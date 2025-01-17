package ru.tde.films.Services;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tde.films.Domain.Comment;
import ru.tde.films.Repositories.*;
import ru.tde.films.Domain.Film;
import ru.tde.films.Repositories.Specifications.*;

@Service
public class FilmService {
    @Autowired
    private FilmRepository repo;

    @Autowired
    private DirectorRepository dirRepo;

    @Autowired
    private ActorRepository actRepo;

    @Autowired
    private CommentRepository commRepo;


    public List<Film> getAll() { return repo.findAll(); }


    public int getPagesCount(int size) { return (int) (repo.count() / size); }


    public List<Film> getFilteredPagination(BaseSpecification<Film> filter, int page, int size) {
        var spec = filter.buildSpecification();
        var sort = filter.buildSort();
        return repo.findAll(spec, PageRequest.of(page, size, sort)).toList();
    }

    public Film getById(int id) {
        return repo.findOne(new FilmSpecification()
                .hasId(id)
                .buildSpecification()
                ).orElse(null);
    }

    public void saveFilm(Film film) {
        repo.save(film);
    }

    public void deleteFilm(Film film) { repo.delete(film); }

    public void addComment(Film film, Comment comment) {
        film.getComments().add(comment);
        comment.setFilm(film);
        repo.save(film);
    }

    public void removeComment(Film film, Comment comment) {
        film.getComments().remove(comment);
        comment.setFilm(null);
        commRepo.delete(comment);
        repo.save(film);
    }
}
