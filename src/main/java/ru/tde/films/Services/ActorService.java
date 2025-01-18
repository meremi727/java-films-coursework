package ru.tde.films.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.tde.films.Domain.Actor;
import ru.tde.films.Domain.Film;
import ru.tde.films.Repositories.ActorRepository;
import ru.tde.films.Repositories.FilmRepository;
import ru.tde.films.Repositories.Specifications.ActorSpecification;
import ru.tde.films.Repositories.Specifications.BaseSpecification;

import java.util.Arrays;
import java.util.List;

@Service
public class ActorService {
    public List<Actor> getAll() { return actorRepo.findAll(); }

    public List<Actor> getFiltered(BaseSpecification<Actor> filter) {
        var spec = filter.buildSpecification();
        var sort = filter.buildSort();
        return actorRepo.findAll(spec, sort);
    }

    public Actor getById(int id) {
        return actorRepo.findOne(new ActorSpecification()
                .hasId(id)
                .buildSpecification()
        ).orElse(null);
    }

    public void saveActor(Actor actor) { actorRepo.save(actor); }

    public void deleteActor(Actor actor) { actorRepo.delete(actor); }

    public void addFilms(Actor actor, Film... films) {
        Arrays.stream(films).forEach(f -> f.getActors().add(actor));
        filmRepo.saveAll(Arrays.stream(films).toList());
    }

    public void removeFilms(Actor actor, Film... films) {
        Arrays.stream(films).forEach(f -> f.getActors().remove(actor));
        filmRepo.saveAll(Arrays.stream(films).toList());
    }

    @Autowired
    private ActorRepository actorRepo;

    @Autowired
    private FilmRepository filmRepo;
}
