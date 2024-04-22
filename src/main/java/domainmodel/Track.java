package domainmodel;

import jakarta.persistence.*;

import java.time.Duration;
import java.util.*;

@Entity
@Table(name = "Tracks")
public abstract class Track extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private Duration duration;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Artist> authors = new ArrayList<>();

    public Track() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        notifyObservers();
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