package dao;


import domainmodel.PodcastPlaylist;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class PodcastPlaylistDao extends BaseDao<PodcastPlaylist> {
    @Override
    public Optional<PodcastPlaylist> get(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        return Optional.ofNullable(em.find(PodcastPlaylist.class, id));
    }

    @Override
    public PodcastPlaylist getByName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em.createQuery("SELECT s FROM PodcastPlaylist s WHERE s.title = :name", PodcastPlaylist.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<PodcastPlaylist> getAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em.createQuery("SELECT s FROM PodcastPlaylist s", PodcastPlaylist.class)
                .getResultList();
    }
}
