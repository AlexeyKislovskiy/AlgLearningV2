package fertdt.alglearningv2.repository.specification;

import fertdt.alglearningv2.model.SituationEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SituationSpecification {
    public static Specification<SituationEntity> methodId(UUID methodId) {
        return (root, query, builder) -> builder.equal(root.get("method").get("id"), methodId);
    }

    public static Specification<SituationEntity> empty() {
        return (root, query, builder) -> builder.equal(root.get("name"), "");
    }

    public static Specification<SituationEntity> all() {
        return (root, query, builder) -> builder.notEqual(root.get("name"), "");
    }

    public static Specification<SituationEntity> situationNameContains(String name) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<SituationEntity> situationNameContains(List<String> names) {
        Specification<SituationEntity> specification = Specification.where(empty());
        for (String name : names) {
            specification = specification.or(situationNameContains(name));
        }
        return specification;
    }

    public static Specification<SituationEntity> situationNameNotContains(String name) {
        return (root, query, builder) -> builder.notLike(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<SituationEntity> situationNameNotContains(List<String> names) {
        Specification<SituationEntity> specification = Specification.where(null);
        for (String name : names) {
            specification = specification.and(situationNameNotContains(name));
        }
        return specification;
    }

    public static Specification<SituationEntity> situationNameStartsWith(String name) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("name")), name.toLowerCase() + "%");
    }

    public static Specification<SituationEntity> situationNameStartsWith(List<String> names) {
        Specification<SituationEntity> specification = Specification.where(empty());
        for (String name : names) {
            specification = specification.or(situationNameStartsWith(name));
        }
        return specification;
    }

    public static Specification<SituationEntity> situationNameNotStartsWith(String name) {
        return (root, query, builder) -> builder.notLike(builder.lower(root.get("name")), name.toLowerCase() + "%");
    }

    public static Specification<SituationEntity> situationNameNotStartsWith(List<String> names) {
        Specification<SituationEntity> specification = Specification.where(null);
        for (String name : names) {
            specification = specification.and(situationNameNotStartsWith(name));
        }
        return specification;
    }

    public static Specification<SituationEntity> situationNameEndsWith(String name) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase());
    }

    public static Specification<SituationEntity> situationNameEndsWith(List<String> names) {
        Specification<SituationEntity> specification = Specification.where(empty());
        for (String name : names) {
            specification = specification.or(situationNameEndsWith(name));
        }
        return specification;
    }

    public static Specification<SituationEntity> situationNameNotEndsWith(String name) {
        return (root, query, builder) -> builder.notLike(builder.lower(root.get("name")), "%" + name.toLowerCase());
    }

    public static Specification<SituationEntity> situationNameNotEndsWith(List<String> names) {
        Specification<SituationEntity> specification = Specification.where(null);
        for (String name : names) {
            specification = specification.and(situationNameNotEndsWith(name));
        }
        return specification;
    }

    public static Specification<SituationEntity> situationNameEquals(String name) {
        return (root, query, builder) -> builder.equal(builder.lower(root.get("name")), name.toLowerCase());
    }

    public static Specification<SituationEntity> situationNameEquals(List<String> names) {
        Specification<SituationEntity> specification = Specification.where(empty());
        for (String name : names) {
            specification = specification.or(situationNameEquals(name));
        }
        return specification;
    }

    public static Specification<SituationEntity> situationNameNotEquals(String name) {
        return (root, query, builder) -> builder.notEqual(builder.lower(root.get("name")), name.toLowerCase());
    }

    public static Specification<SituationEntity> situationNameNotEquals(List<String> names) {
        Specification<SituationEntity> specification = Specification.where(null);
        for (String name : names) {
            specification = specification.and(situationNameNotEquals(name));
        }
        return specification;
    }

    public static Specification<SituationEntity> include(List<String> contains, List<String> startsWith, List<String> endsWith, List<String> equals) {
        if (contains.isEmpty() && startsWith.isEmpty() && endsWith.isEmpty() && equals.isEmpty()) return all();
        else return Specification.where(situationNameContains(contains)).or(situationNameStartsWith(startsWith))
                .or(situationNameEndsWith(endsWith)).or(situationNameEquals(equals));
    }

    public static Specification<SituationEntity> exclude(List<String> notContains, List<String> notStartsWith, List<String> notEndsWith, List<String> notEquals) {
        return Specification.where(situationNameNotContains(notContains)).and(situationNameNotStartsWith(notStartsWith))
                .and(situationNameNotEndsWith(notEndsWith)).and(situationNameNotEquals(notEquals));
    }

    public static Specification<SituationEntity> searchExpression(String searchExpression, UUID methodId) {
        String[] expressions = searchExpression.split("\\|");
        List<String> contains = new ArrayList<>(), startsWith = new ArrayList<>(), endsWith = new ArrayList<>(), equals = new ArrayList<>(),
                notContains = new ArrayList<>(), notStartsWith = new ArrayList<>(), notEndsWith = new ArrayList<>(), notEquals = new ArrayList<>();
        for (String expression : expressions) {
            expression = expression.strip();
            if (expression.startsWith("~")) {
                expression = expression.substring(1);
                if (expression.startsWith("^")) {
                    expression = expression.substring(1);
                    if (expression.endsWith("$")) {
                        expression = expression.substring(0, expression.length() - 1);
                        if (!expression.isEmpty()) notEquals.add(expression);
                    } else if (!expression.isEmpty()) notStartsWith.add(expression);
                } else if (expression.endsWith("$")) {
                    expression = expression.substring(0, expression.length() - 1);
                    if (!expression.isEmpty()) notEndsWith.add(expression);
                } else if (!expression.isEmpty()) notContains.add(expression);
            } else {
                if (expression.startsWith("^")) {
                    expression = expression.substring(1);
                    if (expression.endsWith("$")) {
                        expression = expression.substring(0, expression.length() - 1);
                        if (!expression.isEmpty()) equals.add(expression);
                    } else if (!expression.isEmpty()) startsWith.add(expression);
                } else if (expression.endsWith("$")) {
                    expression = expression.substring(0, expression.length() - 1);
                    if (!expression.isEmpty()) endsWith.add(expression);
                } else if (!expression.isEmpty()) contains.add(expression);
            }
        }
        return Specification.where(methodId(methodId)).and(include(contains, startsWith, endsWith, equals)).and(exclude(notContains, notStartsWith, notEndsWith, notEquals));
    }
}
