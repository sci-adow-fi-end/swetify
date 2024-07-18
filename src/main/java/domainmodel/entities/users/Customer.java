package domainmodel.entities.users;

import domainmodel.entities.BaseEntity;
import domainmodel.entities.collections.PodcastPlaylist;
import domainmodel.entities.collections.SongPlaylist;
import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "Customers")
public class Customer extends BaseEntity {
    @Column(unique = true)
    private String username;

    private String password;

    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    final private List<SongPlaylist> songPlaylists = new LinkedList<>();

    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    final private List<PodcastPlaylist> podcastPlaylists = new LinkedList<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    final private List<Artist> followedArtists = new LinkedList<>();

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Customer() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SongPlaylist> getSongPlaylists() {
        return songPlaylists;
    }


    public List<PodcastPlaylist> getPodcastPlaylists() {
        return podcastPlaylists;
    }


    public List<Artist> getFollowedArtists() {
        return followedArtists;
    }

    public void addFollowedArtists(Artist followedArtist) {
        followedArtists.add(followedArtist);
    }

    public void removeFollowedArtists(Artist followedArtist) {
        followedArtists.remove(followedArtist);
    }

}