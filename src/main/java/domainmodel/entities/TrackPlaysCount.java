package domainmodel.entities;

import domainmodel.entities.track.Track;
import jakarta.persistence.*;

@Entity
@Table(name = "Suggestions",uniqueConstraints = { @UniqueConstraint(columnNames = { "track", "customer" }) })
public class TrackPlaysCount extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private User customer;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Track track;


    private int plays;

    public TrackPlaysCount(){}

    public TrackPlaysCount(User customer, Track t, int plays) {
        this.customer = customer;
        this.track = t;
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