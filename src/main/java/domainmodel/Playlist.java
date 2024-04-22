package domainmodel;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "Playlists")
public abstract class Playlist<T extends Track> extends Model {
    private String title;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<T> tracks = new ArrayList<>();

    public Playlist() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }

    public List<T> getTracks() {
        return tracks;
    }

    public void setTracks(List<T> tracks) {
        this.tracks = tracks;
        notifyObservers();
    }

    public void addTrack(T track) {
        tracks.add(track);
        notifyObservers();
    }
}