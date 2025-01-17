package ru.tde.films.Repositories.Specifications;

import ru.tde.films.Domain.*;

public class ActorSpecification extends PersonSpecification<Actor> {

    public ActorSpecification hasGender(Gender gender) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                gender == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("gender"), gender)
        );
    }

    public ActorSpecification hasCountry(String country) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                country == null || country.isBlank() ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("country"), country)
        );
    }
}
