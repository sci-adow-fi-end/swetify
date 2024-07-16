package dao;

import domainmodel.entities.track.Song;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class SongDao extends BaseDao<Song> {

    @Override
    public Optional<Song> get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Song result = entityManager.find(Song.class, id);
        entityManager.close();
        return Optional.ofNullable(result);
    }

    @Override
    public Song getByName(String name){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s WHERE LOWER(s.title) LIKE LOWER(:title)", Song.class);
        query.setParameter("title", "%" + name.toLowerCase() + "%");
        Song result = query.getSingleResult();
        entityManager.close();
        return result;
    }

    public List<Song> getAllByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s WHERE LOWER(s.title) LIKE LOWER(:title)", Song.class);
        query.setParameter("title", "%" + name.toLowerCase() + "%");
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
