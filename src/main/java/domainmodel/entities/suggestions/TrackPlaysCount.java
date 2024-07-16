package domainmodel.entities.suggestions;

import domainmodel.entities.BaseEntity;
import domainmodel.entities.User;
import domainmodel.entities.track.Track;
import jakarta.persistence.*;

@Entity
@Table(name = "TrackPlaysCount", uniqueConstraints = {@UniqueConstraint(columnNames = {"track_id", "customer_id"})})
@EntityListeners(TrackPlaysCountListener.class)
public class TrackPlaysCount extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    private User customer;

    @ManyToOne(cascade = CascadeType.ALL)
    private Track track;

    private int plays;

    public TrackPlaysCount() {
    }

    public TrackPlaysCount(User customer, Track track, int plays) {
        this.customer = customer;
        this.track = track;
        this.plays = plays;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }
}
