package dao;

import domainmodel.Artist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ArtistDao extends Dao<Artist> {
    @Override
    public Optional<Artist> get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return Optional.ofNullable(entityManager.find(Artist.class, id));
    }

    @Override
    public Artist getByName(String name){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Artist> query = entityManager.createQuery("SELECT a FROM Artist a WHERE a.stageName = :name", Artist.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    public List<Artist> getAllByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Artist> query = entityManager.createQuery("SELECT a FROM Artist a WHERE a.stageName = :name", Artist.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Artist> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Artist> query = entityManager.createQuery("SELECT a FROM Artist a", Artist.class);
        return query.getResultList();
    }
}
