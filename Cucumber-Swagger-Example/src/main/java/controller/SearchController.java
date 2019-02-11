package uk.gov.ons.collection.controller;


import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.ons.collection.entity.DoodleEntity;
import uk.gov.ons.collection.repository.DoodleRepo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Iterables.toArray;

@RestController
@RequestMapping(value = "/doodles2")
public class SearchController {

    @Autowired
    DoodleRepo doodleRepo;
    @Autowired
    private EntityManagerFactory emf;

    // just test
    @GetMapping(value = "/test/{pk}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getPK(@MatrixVariable Map<String, String> matrixVars) {
        //Map.Entry<String,String> entry = matrixVars.entrySet().iterator().next();
        //return "hello "+entry.getValue();

        return new ResponseEntity<>(matrixVars, HttpStatus.OK);
    }

    // using hibernate and a string select statement
    @GetMapping(value = "/s3/{surName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String findByLogin(@PathVariable String surName) {
        EntityManager entityManager = emf.createEntityManager();

        javax.persistence.Query query = entityManager.createQuery("select firstName from DoodleEntity where surName=?1");

        query.setParameter(1, surName);

        return (String) query.getSingleResult();
    }

    // using hibernate and lucene for a single field search
    @GetMapping(value = "/s4/{search}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DoodleEntity> search4(@MatrixVariable Map<String, String> matrixVars) {
        EntityManager entityManager = emf.createEntityManager();

        FullTextEntityManager fullTextEntityManager
                = Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(DoodleEntity.class)
                .get();

        Map.Entry<String, String> entry1 = matrixVars.entrySet().iterator().next();
        Map.Entry<String, String> entry2 = matrixVars.entrySet().iterator().next();

        org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .onFields(entry1.getKey())
                .matching(entry1.getValue())
                .createQuery();

        org.hibernate.search.jpa.FullTextQuery jpaQuery
                = fullTextEntityManager.createFullTextQuery(query, DoodleEntity.class);

        System.out.println(jpaQuery.toString());

        List<DoodleEntity> results = jpaQuery.getResultList();

        return results;
    }

    // using hibernate,lucene and boolean conjunction
    @GetMapping(value = "/s5/{search}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DoodleEntity> search5(@MatrixVariable Map<String, String> matrixVars) {
        EntityManager entityManager = emf.createEntityManager();
        FullTextEntityManager fullTextEntityManager
                = Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(DoodleEntity.class)
                .get();

        Query query = buildMustMatchQuery(queryBuilder, matrixVars, true);

        org.hibernate.search.jpa.FullTextQuery myQuery
                = fullTextEntityManager.createFullTextQuery(query, DoodleEntity.class);

        System.out.println(myQuery.toString());
        List<DoodleEntity> results = myQuery.getResultList();

        return results;
    }

    private Query buildMustMatchQuery(QueryBuilder queryBuilder,
                                      Map<String, String> fieldValues,
                                      boolean mustMatch) {
        BooleanJunction<?> conjunction = queryBuilder.bool();
        for (Map.Entry<String, String> entry : fieldValues.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();

            if (mustMatch) {
                conjunction = conjunction.must(queryBuilder.keyword()
                        .onField(fieldName).matching(value).createQuery());
            } else {
                conjunction = conjunction.should(queryBuilder.keyword()
                        .onField(fieldName).matching(value).createQuery());
            }
        }
        return conjunction.createQuery();
    }

    // using hibernate specifications and predicate with JPA
    @GetMapping(value = "/s6/{search}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DoodleEntity> s6(@MatrixVariable Map<String, String> matrixVars) {
        Specification<DoodleEntity> spec = Specification
                .where(
                        (rootSelect, query, criteriaBuilder) -> {
                            List<Predicate> predicates = new ArrayList<>();
                            for (Map.Entry<String, String> entry : matrixVars.entrySet()) {
                                String fieldName = entry.getKey();
                                String value = entry.getValue();
                                predicates.add(criteriaBuilder.and(
                                        criteriaBuilder.equal(rootSelect.get(fieldName), value)));
                            }

                            return criteriaBuilder.and(toArray(predicates, Predicate.class));
                        });
        List<DoodleEntity> doodles = doodleRepo.findAll(spec);

        return doodles;
    }

    // using hibernate criteria builder and predicates
    @GetMapping(value = "/s7/{search}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<DoodleEntity> s7(@MatrixVariable Map<String, String> matrixVars) {
        CriteriaBuilder builder = emf.getCriteriaBuilder();
        CriteriaQuery<DoodleEntity> query = builder.createQuery(DoodleEntity.class);
        Root<DoodleEntity> root = query.from(DoodleEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, String> entry : matrixVars.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            predicates.add(builder.equal(root.get(fieldName), value));
        }

        query.where(toArray(predicates, Predicate.class));

        List<DoodleEntity> doodles = emf.createEntityManager().createQuery(query.select(root)).getResultList();

        return doodles;
    }


}
