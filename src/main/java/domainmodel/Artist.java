package domainmodel;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "Artists")
public class Artist extends Model {

    // TODO: should artist incapsulate a User?
    private String username;

    private String password;

    private String stageName;

    private String biography;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Album> albums = new ArrayList<>();
    @Transient
    private int followers;

    public Artist() {
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

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
        notifyObservers();
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
        notifyObservers();
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
        notifyObservers();
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
        notifyObservers();
    }
}