package dao;

import domainmodel.entities.Album;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class AlbumDao extends BaseDao<Album> {

    @Override
    public Optional<Album> get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Album result = entityManager.find(Album.class, id);
        entityManager.close();
        return Optional.ofNullable(result);
    }

    @Override
    public Album getByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Album> query = entityManager.createQuery("SELECT a FROM Album a JOIN a.playlist p WHERE LOWER(p.title) LIKE LOWER(:name)",
                Album.class);
        query.setParameter("name", "%" + name.toLowerCase() + "%");
        Album result = query.getSingleResult();
        entityManager.close();
        return result;
    }

    @Override
    public List<Album> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Album> query = entityManager.createQuery("SELECT a FROM Album a", Album.class);
        List<Album> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }
}
