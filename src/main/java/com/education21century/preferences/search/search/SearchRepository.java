package com.education21century.preferences.search.search;

import com.education21century.preferences.search.preference.Preference;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        return (Set<Preference>) getFullTextQuery(Preference.class, search).getResultStream().collect(Collectors.toSet());
    }
}
