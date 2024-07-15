package domainmodel.entities.playlist;

import domainmodel.entities.track.Podcast;
import jakarta.persistence.Entity;

@Entity
public class PodcastPlaylist extends Playlist<Podcast>{

    public PodcastPlaylist(){
        super();
    }
    public void addPodcast(Podcast p){
        tracks.add(p);
    }
}