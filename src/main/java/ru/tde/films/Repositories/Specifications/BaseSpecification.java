package ru.tde.films.Repositories.Specifications;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import oshi.util.tuples.Pair;

/**
 * Абстрактный класс спецификации отбора сущностей.
 */
public class BaseSpecification<T> {

    protected Specification<T> specification;
    protected Sort sort;

    public BaseSpecification() {
        specification = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        sort = Sort.unsorted();
    }

    public <T2 extends BaseSpecification<T>> T2 withSort(Sort sort) {
        this.sort = sort;
        return (T2) this;
    }

    public Specification<T> buildSpecification() { return specification; }

    public Sort buildSort() { return sort; }

    public Pair<Specification<T>, Sort> build() { return new Pair<>(specification, sort); }

    public <T2 extends BaseSpecification<T>> T2 orderBy(String parameterName) {
        sort = sort.and(Sort.by(Sort.Direction.ASC, parameterName));
        return (T2) this;
    }

    public <T2 extends BaseSpecification<T>> T2 orderDescendingBy(String parameterName) {
        sort = sort.and(Sort.by(Sort.Direction.DESC, parameterName));
        return (T2) this;
    }

    protected <T2 extends BaseSpecification<T>> T2 addSpecificationWrapper(Specification<T> spec) {
        specification = specification.and(spec);
        return (T2) this;
    }
}
