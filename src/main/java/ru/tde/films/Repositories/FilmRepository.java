package ru.tde.films.Repositories;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.tde.films.Domain.Film;

public interface FilmRepository extends PagingAndSortingRepository<Film, Integer>, JpaSpecificationExecutor<Film> { }
