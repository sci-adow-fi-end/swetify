package domainmodel.entities.users;

import domainmodel.entities.BaseEntity;
import domainmodel.entities.collections.PodcastPlaylist;
import domainmodel.entities.collections.SongPlaylist;
import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "Customers")
public class Customer extends BaseUser {
    @Column(unique = true)

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    final private List<Artist> followedArtists = new LinkedList<>();

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Customer() {
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