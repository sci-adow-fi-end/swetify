package domainmodel;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "SwetifyUsers")
public class User extends Model {
    private String username;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Playlist<Track>> playlists = new LinkedList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Playlist<Song> favouriteSongs = new Playlist<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Playlist<Podcast> favouritePodcasts = new Playlist<>();

    /*
    TODO: decide how suggestions should be implemented
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Suggestions suggestions;
    */

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Artist> followedArtists = new LinkedList<>();

    // TODO: add map to keep track of how many times a user has listened to a track

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
        notifyObservers();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyObservers();
    }

    public List<Playlist<Track>> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist<Track>> playlists) {
        this.playlists = playlists;
        notifyObservers();
    }

    public List<Artist> getFollowedArtists() {
        return followedArtists;
    }

    public void setFollowedArtists(List<Artist> followedArtists) {
        this.followedArtists = followedArtists;
        notifyObservers();
    }

    public Playlist<Song> getFavouriteSongs() {
        return favouriteSongs;
    }

    public void setFavouriteSongs(Playlist<Song> favouriteSongs) {
        this.favouriteSongs = favouriteSongs;
        notifyObservers();
    }

    public Playlist<Podcast> getFavouritePodcasts() {
        return favouritePodcasts;
    }

    public void setFavouritePodcasts(Playlist<Podcast> favouritePodcasts) {
        this.favouritePodcasts = favouritePodcasts;
        notifyObservers();
    }
}