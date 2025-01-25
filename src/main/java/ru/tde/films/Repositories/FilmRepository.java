package ru.tde.films.Repositories;

import java.util.*;
import org.springframework.data.jpa.repository.*;
import ru.tde.films.Domain.Film;

public interface FilmRepository extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Film> {}
