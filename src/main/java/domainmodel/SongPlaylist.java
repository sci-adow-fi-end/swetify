package domainmodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "SongPlaylists")
public class SongPlaylist extends Playlist<Song>{

    public SongPlaylist(){
        super();
    }
    public void addSong(Song s){
        tracks.add(s);
    }
}
