package domainmodel;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "SwetifyUsers")
public class User extends Model {
    private String username;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Playlist<Track>> playlists = new LinkedList<>();

    /* TODO: decide how favorites should be implemented
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Playlist<> favourites;

    TODO: decide how suggestions should be implemented
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Suggestions suggestions;
    */

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Artist> followedArtists = new LinkedList<>();

    public User() {}

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

    /*public Playlist<Track> getFavourites() {
        return favourites;
    }

    public void setFavourites(Playlist<Track> favourites) {
        this.favourites = favourites;
        notifyObservers();
    }*/

    public List<Artist> getFollowedArtists() {
        return followedArtists;
    }

    public void setFollowedArtists(List<Artist> followedArtists) {
        this.followedArtists = followedArtists;
        notifyObservers();
    }
}