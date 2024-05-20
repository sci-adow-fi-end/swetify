package domainmodel;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Albums")
public class Album extends Model {
    @OneToOne(cascade = CascadeType.ALL)
    private Playlist<Song> playlist;
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    public Album() {
    }

    public Playlist<Song> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist<Song> playlist) {
        this.playlist = playlist;
        notifyObservers();
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        notifyObservers();
    }
}