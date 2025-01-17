package ru.tde.films.Repositories.Specifications;

import ru.tde.films.Domain.Comment;

import java.util.Date;

public class CommentSpecification extends BaseSpecification<Comment> {
    public CommentSpecification hasId(int id) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id));
    }

    public CommentSpecification hasName(String name) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                name == null || name.isBlank() ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("name"), name));
    }

    public CommentSpecification hasScoreGreaterOrEqualThan(int score) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("score"), score));
    }

    public CommentSpecification hasScoreLessOrEqualThan(int score) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("score"), score));
    }

    public CommentSpecification dateWrittenAfter(Date date) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                date == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.greaterThanOrEqualTo(root.get("dateWritten"), date));
    }

    public CommentSpecification dateWrittenBefore(Date date) {
        return addSpecificationWrapper((root, query, criteriaBuilder) ->
                date == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.lessThanOrEqualTo(root.get("dateWritten"), date));
    }
}
