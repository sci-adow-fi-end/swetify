package domainmodel;

import jakarta.persistence.*;

import java.time.Duration;
import java.util.*;

@Entity
@Table(name = "Tracks")
public abstract class Track extends Model {
    private String title;

    @Convert(converter = domainmodel.types.DurationConverter.class)
    private Duration duration;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Artist> authors = new ArrayList<>();

    public Track(String title, int minutes, int seconds, List<Artist> authors) {
        this.title=title;
        this.duration = Duration.ofSeconds(seconds + 60L *minutes);
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
        notifyObservers();
    }

    public List<Artist> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Artist> artists) {
        this.authors = artists;
        notifyObservers();
    }
}