package domainmodel.entities.collections;

import domainmodel.entities.BaseEntity;
import domainmodel.entities.tracks.Song;
import domainmodel.entities.users.Artist;
import domainmodel.entities.users.BaseUser;
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

    public Album(){
        this.playlist = new Playlist<>();
    }

    public Album(String title, List<Song> trackList, Artist author) {
        this.playlist = new Playlist<>(title,author,trackList);
        this.releaseDate = new Date();
    }

    public BaseUser getAuthor() {
        return playlist.getAuthor();
    }


    public List<Song> getSongs(){
        return playlist.getTracks();
    }

    public String getTitle(){
        return playlist.getTitle();
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

}