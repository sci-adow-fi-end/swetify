package domainmodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "PodcastPlaylists")
public class PodcastPlaylist extends Playlist<Podcast>{

    public PodcastPlaylist(){
        super();
    }
    public void addPodcast(Podcast p){
        tracks.add(p);
    }
}
