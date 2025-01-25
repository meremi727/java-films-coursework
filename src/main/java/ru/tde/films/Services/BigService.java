package ru.tde.films.Services;

import java.util.*;
import java.util.stream.Collectors;

import org.javatuples.Pair;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tde.films.Domain.*;
import ru.tde.films.Repositories.*;
import ru.tde.films.Repositories.Dto.ActorDto;
import ru.tde.films.Repositories.Dto.CommentDto;
import ru.tde.films.Repositories.Dto.DirectorDto;
import ru.tde.films.Repositories.Dto.FilmDto;
import ru.tde.films.Repositories.Specifications.*;


@Transactional
@Service
public class BigService {
    public List<FilmDto> getFilteredFilms(BaseSpecification<Film, FilmDto> filter) {
        var spec = filter.buildSpecification();
        var sort = filter.buildSort();
        return map(filmRepo.findAll(spec, sort), FilmDto.class);
    }

    public void saveFilm(FilmDto film) {
        var entity = mapper.map(film, Film.class);
        entity.setActors(new HashSet<>(actorRepo.findAllById(
                entity.getActors()
                        .stream()
                        .map(Actor::getId)
                        .toList())));

        entity.setDirectors(new HashSet<>(directorRepo.findAllById(
                entity.getDirectors()
                        .stream()
                        .map(Director::getId)
                        .toList())));
        filmRepo.save(entity);
    }

    public void deleteFilm(FilmDto film) {
        filmRepo.delete(mapper.map(film, Film.class));
    }

    public List<ActorDto> getAllActors() {
        return map(actorRepo.findAll(), ActorDto.class);
    }

    public List<ActorDto> getFilteredActors(BaseSpecification<Actor, ActorDto> filter) {
        var spec = filter.buildSpecification();
        var sort = filter.buildSort();
        return map(actorRepo.findAll(spec, sort), ActorDto.class);
    }

    public void saveActor(ActorDto actor) {
        actorRepo.save(mapper.map(actor, Actor.class));
    }

    public void deleteActor(ActorDto actor) {
        actorRepo.delete(mapper.map(actor, Actor.class));
    }

    public List<DirectorDto> getAllDirectors() {
        return map(directorRepo.findAll(), DirectorDto.class);
    }

    public List<DirectorDto> getFilteredDirectors(BaseSpecification<Director, DirectorDto> filter) {
        var spec = filter.buildSpecification();
        var sort = filter.buildSort();
        return map(directorRepo.findAll(spec, sort), DirectorDto.class);
    }

    public void saveDirector(DirectorDto director) {
        directorRepo.save(mapper.map(director, Director.class));
    }

    public void deleteDirector(DirectorDto director) {
        directorRepo.delete(mapper.map(director, Director.class));
    }

    public void addComment(Integer filmId, CommentDto comment) {
        var film = filmRepo.findById(filmId).get();
        film.getComments().add(mapper.map(comment, Comment.class));
        filmRepo.save(film);
    }

    public Map<Integer, Long> getCommentsScoresCount() {
        return commentRepo
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(Comment::getScore, Collectors.counting()));
    }

    public Map<String, Long> getGenreCount() {
        return filmRepo
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(Film::getGenre, Collectors.counting()));
    }

    public Map<String, Long> getActorsCountryCount() {
        return actorRepo
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(Actor::getCountry, Collectors.counting()));
    }

    private <S, D> List<D> map(List<S> source, Class<D> destination) {
        return source.stream()
                .map(i -> mapper.map(i, destination))
                .toList();
    }

    @Autowired
    private FilmRepository filmRepo;

    @Autowired
    private ActorRepository actorRepo;

    @Autowired
    private DirectorRepository directorRepo;

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private ModelMapper mapper;
}
