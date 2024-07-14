package dao;

import domainmodel.Album;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class AlbumDao extends Dao<Album> {

    public AlbumDao(){
        setUp();
    }

    @Override
    public Optional<Album> get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return Optional.ofNullable(entityManager.find(Album.class, id));
    }

    @Override
    public Album getByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Album> query = entityManager.createQuery("SELECT a FROM Album a JOIN a.playlist p WHERE p.title = :name", Album.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<Album> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Album> query = entityManager.createQuery("SELECT a FROM Album a", Album.class);
        return query.getResultList();
    }
}
