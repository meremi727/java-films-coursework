package ru.tde.films.Repositories;

import org.springframework.data.jpa.repository.*;
import ru.tde.films.Domain.Director;

public interface DirectorRepository extends JpaRepository<Director, Integer>, JpaSpecificationExecutor<Director> { }