package domainmodel;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "Albums")
public class Album extends Model {
    @OneToOne(cascade = CascadeType.ALL)
    private SongPlaylist playlist;
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    public Album() {
    }

    public SongPlaylist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(SongPlaylist playlist) {
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