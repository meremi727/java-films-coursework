package ru.tde.films.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tde.films.Domain.Actor;
import ru.tde.films.Repositories.ActorRepository;
import ru.tde.films.Repositories.Specifications.ActorSpecification;
import ru.tde.films.Repositories.Specifications.BaseSpecification;

import java.util.List;

@Service
public class ActorService {
    @Autowired
    private ActorRepository repo;

    public List<Actor> getAll() { return repo.findAll(); }

    public int getPagesCount(int size) { return (int) (repo.count() / size); }

    public List<Actor> getFilteredPagination(BaseSpecification<Actor> filter, int page, int size) {
        var spec = filter.buildSpecification();
        var sort = filter.buildSort();
        return repo.findAll(spec, PageRequest.of(page, size, sort)).toList();
    }

    public Actor getById(int id) {
        return repo.findOne(new ActorSpecification()
                .hasId(id)
                .buildSpecification()
        ).orElse(null);
    }

    public void saveActor(Actor actor) {
        repo.save(actor);
    }

    public void deleteActor(Actor actor) { repo.delete(actor); }
}
