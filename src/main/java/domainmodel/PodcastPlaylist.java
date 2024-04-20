package domainmodel;

import jakarta.persistence.*;

@Entity
@Table(name = "PodcastPlaylists")
public class PodcastPlaylist extends Playlist<Podcast> {
    public PodcastPlaylist() {}
}