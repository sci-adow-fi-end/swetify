package domainmodel.entities.collections;

import domainmodel.entities.BaseEntity;
import domainmodel.entities.tracks.Song;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Albums")
public class Album extends BaseEntity {
    @OneToOne(cascade = CascadeType.ALL)
    private Playlist<Song> playlist;
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    public Album(){
        this.playlist = new Playlist<>();
    }

    public Album(String title, List<Song> trackList) {
        this.playlist = new Playlist<>(title,trackList);
        this.releaseDate = new Date();
    }

    public Playlist<Song> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist<Song> playlist) {
        this.playlist = playlist;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

}