package com.education21century.preferences.search.search;

import com.education21century.preferences.search.preference.Preference;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class SearchRepository {

    private final EntityManager entityManager;

    public SearchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private FullTextQuery getFullTextQuery(Class<?> initial, Map<Object, String[]> map) {

        var qb = getQueryBuilder(initial);
        var bj = getBooleanJunction(initial);

        map.forEach((k, v) -> bj.should(qb.keyword().onFields(v).matching(k).createQuery()));

        return getFullTextFromBoolean(bj, initial);
    }

    public QueryBuilder getQueryBuilder(Class<?> initial) {
        var fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(initial)
                .get();
    }

    public BooleanJunction getBooleanJunction(Class<?> initial) {
        var fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(initial)
                .get()
                .bool();
    }

    public FullTextQuery getFullTextFromBoolean(BooleanJunction bj, Class<?> initial) {
        var fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        return fullTextEntityManager.createFullTextQuery(bj.createQuery(), initial);
    }

    @SuppressWarnings("unchecked")
    public Set<Preference> findPreferences(Map<Object, String[]> search) {
        var projection = (Stream<Object[]>) getFullTextQuery(Preference.class, search)
                .setProjection("id", "createdAt", "author", "title", "backgroundImage", "rating")
                .getResultStream();
        return projection.map(o -> Preference
                .builder()
                .id((UUID) o[0])
                .createdAt((LocalDate) o[1])
                .author((String) o[2])
                .title((String) o[3])
                .backgroundImage((String) o[4])
                .rating((Double) o[5])
                .build()
        )
                .collect(Collectors.toSet());
    }
}
