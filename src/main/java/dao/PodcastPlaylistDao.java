package dao;


import domainmodel.PodcastPlaylist;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class PodcastPlaylistDao extends BaseDao<PodcastPlaylist> {
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
        PodcastPlaylist result = em.createQuery("SELECT s FROM PodcastPlaylist s WHERE s.title = :name", PodcastPlaylist.class)
                .setParameter("name", name)
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
        List<PodcastPlaylist> resultList = em.createQuery("SELECT s FROM PodcastPlaylist s WHERE s.title = :name", PodcastPlaylist.class)
                .setParameter("name", name)
                .getResultList();
        em.close();
        return resultList;
    }
}
