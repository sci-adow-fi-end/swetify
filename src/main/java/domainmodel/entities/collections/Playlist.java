package domainmodel.entities.collections;

import domainmodel.entities.BaseEntity;
import domainmodel.entities.tracks.Track;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "Playlists")
public class Playlist<T extends Track> extends BaseEntity implements Iterable<T> {
    protected String title;
    @ManyToMany(cascade = CascadeType.ALL, targetEntity = Track.class)
    protected List<T> tracks = new ArrayList<>();

    public Playlist(){
    }
    public Playlist(String title) {
        this.title=title;
    }
    public Playlist(String title, List<T> trackList) {
        this.title=title;
        this.tracks=trackList;
    }

    public String getTitle() {
        return title;
    }

    public void removeTrack(int i){
        tracks.remove(i);
    }

    public void setTitle(String title) {
        this.title = title;

    }

    public List<T> getTracks() {
        return tracks;
    }

    public void setTracks(List<T> tracks) {
        this.tracks = tracks;

    }

    public void addTrack(T track) {
        tracks.add(track);

    }

    public int print(){
        int i=1;
        for (Track track : tracks){
            System.out.println(i+" "+track.getTitle());
            System.out.println("\n");
            i++;
        }
        return i;
    }

    @Override
    public Iterator<T> iterator() {
        return tracks.iterator();
    }
}