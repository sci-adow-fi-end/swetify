package dao.suggestions;

import dao.BaseDAO;
import domainmodel.entities.suggestions.PodcastPlaysCount;
import domainmodel.entities.tracks.Podcast;
import domainmodel.entities.users.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class PodcastPlaysCountDAO extends BaseDAO<PodcastPlaysCount> {

    @Override
    public Optional<PodcastPlaysCount> get(long id) {
        EntityManager entityManager = getEntityManagerFactory().createEntityManager();
        PodcastPlaysCount podcastPlaysCount = entityManager.find(PodcastPlaysCount.class, id);
        entityManager.close();
        return Optional.ofNullable(podcastPlaysCount);
    }

    @Override
    public PodcastPlaysCount getByName(String name) {
        return null;
    }

    @Override
    public List<PodcastPlaysCount> getAll() {
        EntityManager entityManager = getEntityManagerFactory().createEntityManager();
        TypedQuery<PodcastPlaysCount> query = entityManager.createQuery("SELECT ppc FROM PodcastPlaysCount ppc", PodcastPlaysCount.class);
        List<PodcastPlaysCount> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }

    public void incrementPlays(long id) {
        executeInsideTransaction(entityManager -> {
            PodcastPlaysCount podcastPlaysCount = entityManager.find(PodcastPlaysCount.class, id);
            if (podcastPlaysCount != null) {
                podcastPlaysCount.setPlays(podcastPlaysCount.getPlays() + 1);
                entityManager.merge(podcastPlaysCount);
            }
        });
    }

    public void incrementOrAddPlayCount(Customer user, Podcast podcast) {
        executeInsideTransaction(entityManager -> {
            try {
                TypedQuery<PodcastPlaysCount> query = entityManager.createQuery(
                        "SELECT ppc FROM PodcastPlaysCount ppc WHERE ppc.customer = :user AND ppc.track = :podcast",
                        PodcastPlaysCount.class);
                query.setParameter("user", user);
                query.setParameter("podcast", podcast);
                PodcastPlaysCount existingEntry = query.getSingleResult();
                existingEntry.setPlays(existingEntry.getPlays() + 1);
                entityManager.merge(existingEntry);
            } catch (NoResultException ex) {
                PodcastPlaysCount newEntry = new PodcastPlaysCount(user, podcast, 1);
                entityManager.persist(newEntry);
            }
        });
    }
}
