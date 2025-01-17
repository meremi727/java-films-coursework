package ru.tde.films.Repositories.Specifications;

import ru.tde.films.Domain.Person;

import java.util.Date;

/**
 * Спецификация отбора персон.
 */
public abstract class PersonSpecification<T extends Person> extends BaseSpecification<T> {
    public <T2 extends PersonSpecification<T>> T2 hasId(int id) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id));
    }

    public <T2 extends PersonSpecification<T>> T2 hasSurname(String surname) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                surname == null || surname.isBlank() ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("surname"), surname)
        );
    }

    public <T2 extends PersonSpecification<T>> T2 hasName(String name) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                name == null || name.isBlank() ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("name"), name)
        );
    }

    public <T2 extends PersonSpecification<T>> T2 hasPatronymic(String patronymic) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                patronymic == null || patronymic.isBlank() ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("patronymic"), patronymic)
        );
    }

    public <T2 extends PersonSpecification<T>> T2 dateOfBirthAfter(Date date) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                date == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfBirth"), date)
        );
    }

    public <T2 extends PersonSpecification<T>> T2 dateOfBirthBefore(Date date) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                date == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.lessThanOrEqualTo(root.get("dateOfBirth"), date)
        );
    }
}
