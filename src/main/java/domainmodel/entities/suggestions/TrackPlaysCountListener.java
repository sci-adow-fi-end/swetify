package domainmodel.entities.suggestions;

import dao.BaseDAO;
import domainmodel.entities.tracks.Track;
import jakarta.persistence.*;

public class TrackPlaysCountListener {

    @PostPersist
    @PostUpdate
    @PostRemove
    public void updateTrackTotalPlays(TrackPlaysCount trackPlaysCount) {
        EntityManager em = BaseDAO.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();

            Track track = trackPlaysCount.getTrack();
            Query query = em.createQuery(
                    "SELECT SUM(t.plays) FROM TrackPlaysCount t WHERE t.track = :track"
            );
            query.setParameter("track", track);
            Long totalPlays = (Long) query.getSingleResult();
            track.setTotalPlays(totalPlays != null ? totalPlays : 0);

            em.merge(track);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
