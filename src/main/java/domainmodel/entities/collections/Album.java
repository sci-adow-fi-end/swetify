package domainmodel.entities.collections;

import domainmodel.entities.BaseEntity;
import domainmodel.entities.tracks.Song;
import domainmodel.entities.users.Artist;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Albums")
public class Album extends BaseEntity {

    @OneToOne(cascade = CascadeType.PERSIST)
    final private Playlist<Song> playlist;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @ManyToOne
    private Artist author;

    public Album(){
        this.playlist = new Playlist<>();
    }

    public Album(String title, List<Song> trackList, Artist author) {
        this.playlist = new Playlist<>(title,trackList);
        this.releaseDate = new Date();
        this.author = author;
    }

    public Artist getAuthor() {
        return author;
    }


    // TODO: This should return a List and not the Playlist object
    public Playlist<Song> getPlaylist() {
        return playlist;
    }


    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

}