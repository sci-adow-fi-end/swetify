package dao;

import domainmodel.Song;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class SongDao extends Dao<Song> {
    @Override
    public Optional<Song> get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return Optional.ofNullable(entityManager.find(Song.class, id));
    }

    @Override
    public Song getByName(String name){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s WHERE s.title = :title", Song.class);
        query.setParameter("title", name);
        return query.getSingleResult();
    }

    public List<Song> getAllByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s WHERE s.title = :title", Song.class);
        query.setParameter("title", name);
        return query.getResultList();
    }

    @Override
    public List<Song> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s", Song.class);
        return query.getResultList();
    }
}
