package domainmodel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "Playlists")
public class Playlist<T extends Track> extends Model implements Iterable<T>{
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

    @Override
    public Iterator<T> iterator() {
        return tracks.iterator();
    }
}