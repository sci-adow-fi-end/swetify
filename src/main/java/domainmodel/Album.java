package domainmodel;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "Albums")
public class Album extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private SongPlaylist playlist;
    private Date releaseDate;

    public Album() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SongPlaylist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(SongPlaylist playlist) {
        this.playlist = playlist;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}