package dao.tracks;

import dao.BaseDAO;
import domainmodel.entities.tracks.Song;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class SongDAO extends BaseDAO<Song> {

    @Override
    public Optional<Song> get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Song result = entityManager.find(Song.class, id);
        entityManager.close();
        return Optional.ofNullable(result);
    }

    public List<Song> getByTitle(String title) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s WHERE LOWER(s.title) LIKE LOWER(:title)", Song.class);
        query.setParameter("title", "%" + title.toLowerCase() + "%");
        List<Song> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }

    @Override
    public List<Song> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s", Song.class);
        List<Song> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }
}
