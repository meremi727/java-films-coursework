package ru.tde.films.Repositories.Specifications;

import ru.tde.films.Domain.Film;

import java.util.Date;

/**
 * Спецификация отбора фильмов.
 */
public class FilmSpecification extends BaseSpecification<Film> {
    public FilmSpecification hasId(int id) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id));
    }

    public FilmSpecification hasTitle(String title) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                title == null || title.isBlank() ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("title"), title)
        );
    }

    public FilmSpecification releasedAfter(Date date) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                date == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.greaterThanOrEqualTo(root.get("dateReleased"), date)
        );
    }

    public FilmSpecification releasedBefore(Date date) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                date == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.lessThanOrEqualTo(root.get("dateReleased"), date)
        );
    }

    public FilmSpecification hasGenre(String genre) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                genre == null || genre.isBlank() ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("genre"), genre)
        );
    }

    public FilmSpecification hasCountry(String country) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                country == null || country.isBlank() ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("country"), country)
        );
    }

    public FilmSpecification hasDurationLessThanOrEqualTo(Integer duration) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                duration == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.lessThanOrEqualTo(root.get("timeDuration"), duration)
        );
    }

    public FilmSpecification hasDurationGreaterThanOrEqualTo(Integer duration) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                duration == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.greaterThanOrEqualTo(root.get("timeDuration"), duration)
        );
    }
}
