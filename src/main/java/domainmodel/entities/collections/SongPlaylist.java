package domainmodel.entities.collections;

import domainmodel.entities.tracks.Song;
import jakarta.persistence.Entity;

@Entity
public class SongPlaylist extends Playlist<Song>{

    public SongPlaylist(){
        super();
    }
    public void addSong(Song s){
        tracks.add(s);
    }
}
