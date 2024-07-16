package dao;


import domainmodel.entities.playlist.PodcastPlaylist;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class PodcastPlaylistDAO extends BaseDAO<PodcastPlaylist> {
    @Override
    public Optional<PodcastPlaylist> get(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        PodcastPlaylist result = em.find(PodcastPlaylist.class, id);
        em.close();
        return Optional.ofNullable(result);
    }

    @Override
    public PodcastPlaylist getByName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        PodcastPlaylist result = em.createQuery("SELECT s FROM PodcastPlaylist s WHERE LOWER(s.title) LIKE LOWER(:name)", PodcastPlaylist.class)
                .setParameter("name", "%" + name.toLowerCase() + "%")
                .getSingleResult();
        em.close();
        return result;
    }

    @Override
    public List<PodcastPlaylist> getAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<PodcastPlaylist> resultList = em.createQuery("SELECT s FROM PodcastPlaylist s", PodcastPlaylist.class)
                .getResultList();
        em.close();
        return resultList;
    }

    public List<PodcastPlaylist> getAllByName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<PodcastPlaylist> resultList = em.createQuery("SELECT s FROM PodcastPlaylist s WHERE LOWER(s.title) LIKE LOWER(:name)", PodcastPlaylist.class)
                .setParameter("name", "%" + name.toLowerCase() + "%")
                .getResultList();
        em.close();
        return resultList;
    }
}
