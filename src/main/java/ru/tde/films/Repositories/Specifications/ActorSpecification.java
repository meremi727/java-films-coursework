package ru.tde.films.Repositories.Specifications;

import ru.tde.films.Domain.*;
import ru.tde.films.Repositories.Dto.ActorDto;

import java.util.Date;

public class ActorSpecification extends BaseSpecification<Actor, ActorDto> {
    public ActorSpecification hasId(int id) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id));
    }

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

    public ActorSpecification hasSurname(String surname) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                surname == null || surname.isBlank() ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("surname"), surname)
        );
    }

    public ActorSpecification hasName(String name) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                name == null || name.isBlank() ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("name"), name)
        );
    }

    public ActorSpecification hasPatronymic(String patronymic) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                patronymic == null || patronymic.isBlank() ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("patronymic"), patronymic)
        );
    }

    public ActorSpecification dateOfBirthAfter(Date date) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                date == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfBirth"), date)
        );
    }

    public ActorSpecification dateOfBirthBefore(Date date) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                date == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.lessThanOrEqualTo(root.get("dateOfBirth"), date)
        );
    }
}
