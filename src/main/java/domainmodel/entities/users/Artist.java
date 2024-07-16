package domainmodel.entities.users;

import domainmodel.entities.BaseEntity;
import domainmodel.entities.collections.Album;
import domainmodel.entities.tracks.Podcast;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Artists")
public class Artist extends BaseEntity {

    @Column(unique = true)
    private String username;

    private String password;

    private String stageName;

    private String biography;

    @ManyToMany(cascade = CascadeType.ALL)
    final private List<Album> albums = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    final private List<Podcast> podcasts = new ArrayList<>();
    @Transient
    private int followers;

    public Artist(){}

    public Artist(String username, String password, String stageName, String biography, int followers) {
        this.username = username;
        this.password = password;
        this.stageName = stageName;
        this.biography = biography;
        this.followers = followers;
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

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;

    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;

    }

    public void addAlbum(Album a){
        this.albums.add(a);
    }

    public void addPodcast(Podcast p){
        this.podcasts.add(p);
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;

    }

    @Override
    public String toString() {
        return
                "" + stageName + '\'' +
                ", biography='" + biography + '\'';

    }
}