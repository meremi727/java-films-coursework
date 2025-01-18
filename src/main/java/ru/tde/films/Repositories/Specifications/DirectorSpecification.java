package ru.tde.films.Repositories.Specifications;

import ru.tde.films.Domain.Director;
import ru.tde.films.Domain.Gender;

import java.util.Date;

public class DirectorSpecification extends BaseSpecification<Director> {
    public DirectorSpecification hasId(int id) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id));
    }

    public DirectorSpecification hasGender(Gender gender) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                gender == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("gender"), gender)
        );
    }

    public DirectorSpecification hasCountry(String country) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                country == null || country.isBlank() ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("country"), country)
        );
    }

    public DirectorSpecification hasSurname(String surname) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                surname == null || surname.isBlank() ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("surname"), surname)
        );
    }

    public DirectorSpecification hasName(String name) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                name == null || name.isBlank() ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("name"), name)
        );
    }

    public DirectorSpecification hasPatronymic(String patronymic) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                patronymic == null || patronymic.isBlank() ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("patronymic"), patronymic)
        );
    }

    public DirectorSpecification dateOfBirthAfter(Date date) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                date == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfBirth"), date)
        );
    }

    public DirectorSpecification dateOfBirthBefore(Date date) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                date == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.lessThanOrEqualTo(root.get("dateOfBirth"), date)
        );
    }

    public DirectorSpecification hasDignity(String dignity) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                dignity == null || dignity.isBlank() ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("dignity"), dignity)
        );
    }
}
