package domainmodel.entities.track;

import domainmodel.entities.Artist;
import domainmodel.entities.BaseEntity;
import jakarta.persistence.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Tracks")
public abstract class Track extends BaseEntity {
    private String title;

    @Convert(converter = domainmodel.types.DurationConverter.class)
    private Duration duration;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Artist> authors = new ArrayList<>();

    private long totalPlays;

    public Track() {
    }

    public Track(String title, int minutes, int seconds, List<Artist> authors) {
        this.title = title;
        this.duration = Duration.ofSeconds(seconds + 60L * minutes);
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public List<Artist> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Artist> artists) {
        this.authors = artists;
    }

    public long getTotalPlays() {
        return totalPlays;
    }

    public void setTotalPlays(long totalPlays) {
        this.totalPlays = totalPlays;
    }

    public void incrementPlays(long increment) {
        this.totalPlays += increment;
    }
}
