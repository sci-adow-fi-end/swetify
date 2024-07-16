package domainmodel.entities;

import domainmodel.entities.playlist.Playlist;
import domainmodel.entities.track.Podcast;
import domainmodel.entities.track.Song;
import domainmodel.entities.track.Track;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "SwetifyUsers")
public class User extends BaseEntity {
    @Column(unique = true)
    private String username;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Playlist<Track>> playlists = new LinkedList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Playlist<Song> favouriteSongs = new Playlist<>("Favourites");

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Playlist<Podcast> favouritePodcasts = new Playlist<>("Favourites");

    /*
    TODO: decide how suggestions should be implemented
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Suggestions suggestions;
    */

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Artist> followedArtists = new LinkedList<>();

    @ElementCollection
    @CollectionTable(name = "User_Track_Counts", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyJoinColumn(name = "track_id")
    @Column(name = "listen_count")
    private Map<Track, Integer> trackListenCounts = new HashMap<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
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

    public List<Playlist<Track>> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist<Track>> playlists) {
        this.playlists = playlists;

    }

    public List<Artist> getFollowedArtists() {
        return followedArtists;
    }

    public void setFollowedArtists(List<Artist> followedArtists) {
        this.followedArtists = followedArtists;

    }

    public Playlist<Song> getFavouriteSongs() {
        return favouriteSongs;
    }

    public void setFavouriteSongs(Playlist<Song> favouriteSongs) {
        this.favouriteSongs = favouriteSongs;

    }

    public Playlist<Podcast> getFavouritePodcasts() {
        return favouritePodcasts;
    }

    public void setFavouritePodcasts(Playlist<Podcast> favouritePodcasts) {
        this.favouritePodcasts = favouritePodcasts;

    }

    public Map<Track, Integer> getTrackListenCounts() {
        return trackListenCounts;
    }

    public void setTrackListenCounts(Map<Track, Integer> trackListenCounts) {
        this.trackListenCounts = trackListenCounts;
    }
}