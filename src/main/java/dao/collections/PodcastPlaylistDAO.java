package dao.collections;


import dao.BaseDAO;
import domainmodel.entities.collections.PodcastPlaylist;
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

    public List<PodcastPlaylist> getByTitle(String title) {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<PodcastPlaylist> result = em.createQuery("SELECT s FROM PodcastPlaylist s WHERE LOWER(s.title) LIKE LOWER(:name)", PodcastPlaylist.class)
                .setParameter("name", "%" + title.toLowerCase() + "%")
                .getResultList();
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
}
