package dao;

import domainmodel.entities.User;
import domainmodel.entities.suggestions.SongPlaysCount;
import domainmodel.entities.track.Song;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class SongPlaysCountDAO extends BaseDAO<SongPlaysCount> {

    @Override
    public Optional<SongPlaysCount> get(long id) {
        EntityManager entityManager = getEntityManagerFactory().createEntityManager();
        SongPlaysCount songPlaysCount = entityManager.find(SongPlaysCount.class, id);
        entityManager.close();
        return Optional.ofNullable(songPlaysCount);
    }

    @Override
    public SongPlaysCount getByName(String name) {
        return null;
    }

    @Override
    public List<SongPlaysCount> getAll() {
        EntityManager entityManager = getEntityManagerFactory().createEntityManager();
        TypedQuery<SongPlaysCount> query = entityManager.createQuery("SELECT spc FROM SongPlaysCount spc", SongPlaysCount.class);
        List<SongPlaysCount> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }

    public void incrementPlays(long id) {
        executeInsideTransaction(entityManager -> {
            SongPlaysCount songPlaysCount = entityManager.find(SongPlaysCount.class, id);
            if (songPlaysCount != null) {
                songPlaysCount.setPlays(songPlaysCount.getPlays() + 1);
                entityManager.merge(songPlaysCount);
            }
        });
    }

    public void incrementOrAddPlayCount(User user, Song song) {
        executeInsideTransaction(entityManager -> {
            try {
                TypedQuery<SongPlaysCount> query = entityManager.createQuery(
                        "SELECT spc FROM SongPlaysCount spc WHERE spc.customer = :user AND spc.track = :song",
                        SongPlaysCount.class);
                query.setParameter("user", user);
                query.setParameter("song", song);
                SongPlaysCount existingEntry = query.getSingleResult();
                existingEntry.setPlays(existingEntry.getPlays() + 1);
                entityManager.merge(existingEntry);
            } catch (NoResultException ex) {
                SongPlaysCount newEntry = new SongPlaysCount(user, song, 1);
                entityManager.persist(newEntry);
            }
        });
    }
}
