package dao;

import domainmodel.SongPlaylist;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class SongPlaylistDao extends BaseDao<SongPlaylist> {

    @Override
    public Optional<SongPlaylist> get(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        return Optional.ofNullable(em.find(SongPlaylist.class, id));
    }

    @Override
    public SongPlaylist getByName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em.createQuery("SELECT s FROM SongPlaylist s WHERE s.title = :name", SongPlaylist.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<SongPlaylist> getAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em.createQuery("SELECT s FROM SongPlaylist s", SongPlaylist.class)
                .getResultList();
    }

    public List<SongPlaylist> getAllByName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em.createQuery("SELECT s FROM SongPlaylist s WHERE s.title = :name", SongPlaylist.class)
                .setParameter("name", name)
                .getResultList();
    }
}
