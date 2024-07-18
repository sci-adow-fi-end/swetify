package dao.collections;

import dao.BaseDAO;
import domainmodel.entities.collections.Album;
import domainmodel.entities.users.Artist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class AlbumDAO extends BaseDAO<Album> {

    @Override
    public Optional<Album> get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Album result = entityManager.find(Album.class, id);
        entityManager.close();
        return Optional.ofNullable(result);
    }

    public List<Album> getByTitle(String title) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Album> query = entityManager.createQuery("SELECT a FROM Album a JOIN a.playlist p WHERE LOWER(p.title) LIKE LOWER(:name)",
                Album.class);
        query.setParameter("name", "%" + title.toLowerCase() + "%");
        List<Album> result = query.getResultList();
        entityManager.close();
        return result;
    }

    public List<Album> getByArtist(Artist artist) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Album> query = entityManager.createQuery("SELECT a FROM Album a JOIN a.playlist p WHERE a.author = :artist", Album.class);
        query.setParameter("artist", artist);
        List<Album> result = query.getResultList();
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
