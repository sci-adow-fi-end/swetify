package dao.collections;

import dao.BaseDAO;
import domainmodel.entities.collections.SongPlaylist;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class SongPlaylistDAO extends BaseDAO<SongPlaylist> {

    @Override
    public Optional<SongPlaylist> get(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        SongPlaylist result = em.find(SongPlaylist.class, id);
        em.close();
        return Optional.ofNullable(result);
    }

    @Override
    public SongPlaylist getByName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        SongPlaylist result = em.createQuery("SELECT s FROM SongPlaylist s WHERE LOWER(s.title) LIKE LOWER(:name)", SongPlaylist.class)
                .setParameter("name", "%" + name.toLowerCase() + "%")
                .getSingleResult();
        em.close();
        return result;
    }

    @Override
    public List<SongPlaylist> getAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<SongPlaylist> resultList = em.createQuery("SELECT s FROM SongPlaylist s", SongPlaylist.class)
                .getResultList();
        em.close();
        return resultList;
    }

    public List<SongPlaylist> getAllByName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<SongPlaylist> resultList = em.createQuery("SELECT s FROM SongPlaylist s WHERE LOWER(s.title) LIKE LOWER(:name)", SongPlaylist.class)
                .setParameter("name", "%" + name.toLowerCase() + "%")
                .getResultList();
        em.close();
        return resultList;
    }
}