package ru.tde.films.Repositories;

import org.springframework.data.jpa.repository.*;
import ru.tde.films.Domain.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor> { }
