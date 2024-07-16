package dao;

import domainmodel.entities.User;
import domainmodel.entities.suggestions.PodcastPlaysCount;
import domainmodel.entities.suggestions.SongPlaysCount;
import domainmodel.entities.suggestions.TrackPlaysCount;
import domainmodel.entities.track.Podcast;
import domainmodel.entities.track.Song;
import domainmodel.entities.track.Track;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SuggestionDAO extends BaseDAO<TrackPlaysCount> {

    @PersistenceContext
    private EntityManager entityManager;

    // Generic method to get top tracks by user
    private <T extends TrackPlaysCount> List<Track> getTopTracksByUser(User user, Class<T> entityClass) {
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

    // Generic method to get users who listened to top tracks
    private <T extends TrackPlaysCount> List<Long> getUsersWhoListenedToTopTracks(List<Track> topTracks, Class<T> entityClass) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<T> root = query.from(entityClass);
        query.select(root.get("customer").get("id")).distinct(true)
                .where(root.get("track").in(topTracks));
        return entityManager.createQuery(query).getResultList();
    }

    // Generic method to get top tracks by users
    private <T extends TrackPlaysCount> List<Track> getTopTracksByUsers(List<Long> users, Class<T> entityClass) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Track> query = cb.createQuery(Track.class);
        Root<T> root = query.from(entityClass);
        query.select(root.get("track"))
                .where(root.get("customer").get("id").in(users))
                .groupBy(root.get("track"))
                .orderBy(cb.desc(cb.sum(root.get("plays"))));
        return entityManager.createQuery(query)
                .setMaxResults(3)
                .getResultList();
    }

    // Method to get top songs by similar users
    public List<Song> getTopSongsBySimilarUsers(User user) {
        entityManager.getTransaction().begin();

        List<Track> top10Tracks = getTopTracksByUser(user, SongPlaysCount.class);
        List<Long> users = getUsersWhoListenedToTopTracks(top10Tracks, SongPlaysCount.class);
        List<Track> topTracksByUsers = getTopTracksByUsers(users, SongPlaysCount.class);

        entityManager.getTransaction().commit();
        entityManager.close();

        // Convert Track objects to Song objects
        return topTracksByUsers.stream()
                .map(track -> (Song) track)
                .collect(Collectors.toList());
    }

    // Method to get top podcasts by similar users
    public List<Podcast> getTopPodcastsBySimilarUsers(User user) {
        entityManager.getTransaction().begin();

        List<Track> top10Tracks = getTopTracksByUser(user, PodcastPlaysCount.class);
        List<Long> users = getUsersWhoListenedToTopTracks(top10Tracks, PodcastPlaysCount.class);
        List<Track> topTracksByUsers = getTopTracksByUsers(users, PodcastPlaysCount.class);

        entityManager.getTransaction().commit();
        entityManager.close();

        // Convert Track objects to Podcast objects
        return topTracksByUsers.stream()
                .map(track -> (Podcast) track)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<TrackPlaysCount> get(long id) {
        return Optional.empty();
    }

    @Override
    public TrackPlaysCount getByName(String name) {
        return null;
    }

    @Override
    public List<TrackPlaysCount> getAll() {
        return List.of();
    }
}
