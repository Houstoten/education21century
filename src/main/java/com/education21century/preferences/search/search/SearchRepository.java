package com.education21century.preferences.search.search;

import com.education21century.preferences.search.preference.Preference;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
        var fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        var qb = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(initial)
                .get();
        var bj = fullTextEntityManager
                .getSearchFactory()
                .buildQueryBuilder()
                .forEntity(initial)
                .get()
                .bool();

        map.forEach((k, v) -> bj.should(qb.keyword().onFields(v).matching(k).createQuery()));

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
                .createdAt((Date) o[1])
                .author((String) o[2])
                .title((String) o[3])
                .backgroundImage((String) o[4])
                .rating((Double) o[5])
                .build()
        )
                .collect(Collectors.toSet());
    }
}
