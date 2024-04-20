package domainmodel;

import jakarta.persistence.*;


@Entity
@Table(name = "SongPlaylists")
public class SongPlaylist extends Playlist<Song> {
    public SongPlaylist() {}
}