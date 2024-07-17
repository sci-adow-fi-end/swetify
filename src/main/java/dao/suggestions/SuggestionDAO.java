package dao.suggestions;

import dao.BaseDAO;
import domainmodel.entities.suggestions.PodcastPlaysCount;
import domainmodel.entities.suggestions.SongPlaysCount;
import domainmodel.entities.suggestions.TrackPlaysCount;
import domainmodel.entities.tracks.Podcast;
import domainmodel.entities.tracks.Song;
import domainmodel.entities.tracks.Track;
import domainmodel.entities.users.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SuggestionDAO extends BaseDAO<TrackPlaysCount> {


    private <T extends TrackPlaysCount> List<Track> getTopTracksByUser(Customer user, Class<T> entityClass) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Track> query = cb.createQuery(Track.class);
        Root<T> root = query.from(entityClass);
        query.select(root.get("track"))
                .where(cb.equal(root.get("customer"), user))
                .orderBy(cb.desc(root.get("plays")));
        return entityManager.createQuery(query)
                .setMaxResults(10)
                .getResultList();
    }

    private <T extends TrackPlaysCount> List<Long> getUsersWhoListenedToTopTracks(List<Track> topTracks, Class<T> entityClass) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<T> root = query.from(entityClass);
        query.select(root.get("customer").get("id")).distinct(true)
                .where(root.get("track").in(topTracks));
        return entityManager.createQuery(query).getResultList();
    }

    private <T extends TrackPlaysCount> List<Track> getTopTracksByUsers(List<Long> users, Class<T> entityClass) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Track> query = cb.createQuery(Track.class);
        Root<T> root = query.from(entityClass);
        query.select(root.get("track"))
                .where(root.get("customer").get("id").in(users))
                .groupBy(root.get("track"))
                .orderBy(cb.desc(cb.sum(root.get("plays"))));
        return entityManager.createQuery(query)
                .setMaxResults(10)
                .getResultList();
    }

    public List<Song> getTopSongsBySimilarUsers(Customer user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Track> top10Tracks = getTopTracksByUser(user, SongPlaysCount.class);
        List<Long> users = getUsersWhoListenedToTopTracks(top10Tracks, SongPlaysCount.class);
        List<Track> topTracksByUsers = getTopTracksByUsers(users, SongPlaysCount.class);

        entityManager.getTransaction().commit();
        entityManager.close();

        return topTracksByUsers.stream()
                .map(track -> (Song) track)
                .collect(Collectors.toList());
    }

    public List<Podcast> getTopPodcastsBySimilarUsers(Customer user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Track> top10Tracks = getTopTracksByUser(user, PodcastPlaysCount.class);
        List<Long> users = getUsersWhoListenedToTopTracks(top10Tracks, PodcastPlaysCount.class);
        List<Track> topTracksByUsers = getTopTracksByUsers(users, PodcastPlaysCount.class);

        entityManager.getTransaction().commit();
        entityManager.close();

        return topTracksByUsers.stream()
                .map(track -> (Podcast) track)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<TrackPlaysCount> get(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TrackPlaysCount result = em.find(TrackPlaysCount.class, id);
        em.close();
        return Optional.ofNullable(result);
    }

    @Override
    public List<TrackPlaysCount> getAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<TrackPlaysCount> resultList = em.createQuery("SELECT s FROM TrackPlaysCount s", TrackPlaysCount.class)
                .getResultList();
        em.close();
        return resultList;
    }
}
