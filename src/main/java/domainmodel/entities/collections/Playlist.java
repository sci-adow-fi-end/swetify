package domainmodel.entities.collections;

import domainmodel.entities.BaseEntity;
import domainmodel.entities.tracks.Track;
import domainmodel.entities.users.BaseUser;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "Playlists")
public class Playlist<T extends Track> extends BaseEntity implements Iterable<T> {
    protected String title;
    @ManyToMany(cascade = CascadeType.MERGE, targetEntity = Track.class, fetch = FetchType.EAGER)
    protected List<T> tracks = new ArrayList<>();



    @ManyToOne
    private BaseUser author;

    public Playlist(){}
    public Playlist(String title,BaseUser author, List<T> trackList) {
        this.title=title;
        this.tracks=trackList;
        this.author = author;
    }
    public BaseUser getAuthor() {
        return author;
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