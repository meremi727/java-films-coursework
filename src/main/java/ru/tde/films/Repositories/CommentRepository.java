package ru.tde.films.Repositories;

import org.springframework.data.jpa.repository.*;
import ru.tde.films.Domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> { }
