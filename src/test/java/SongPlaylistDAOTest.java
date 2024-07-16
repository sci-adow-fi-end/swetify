import dao.SongDAO;
import dao.SongPlaylistDAO;
import domainmodel.entities.playlist.SongPlaylist;
import domainmodel.entities.track.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SongPlaylistDAOTest extends BaseTest {
    SongPlaylistDAO songPlaylistDatabase;
    SongPlaylist songPlaylist1 = new SongPlaylist();
    SongPlaylist songPlaylist2 = new SongPlaylist();
    Song song1 = new Song();
    Song song2 = new Song();
    Song song3 = new Song();

    @BeforeEach
    public void setUp() {
        super.setUp();
        songPlaylistDatabase = new SongPlaylistDAO();
        SongDAO songDatabase = new SongDAO();
        songPlaylist1.setTitle("playlist1");
        songPlaylist2.setTitle("playlist2");
        songPlaylistDatabase.save(songPlaylist1);
        songPlaylistDatabase.save(songPlaylist2);

        song1.setTitle("title1");
        song1.setDuration(Duration.ofSeconds(10));
        songDatabase.save(song1);
        song2.setTitle("title2");
        song2.setDuration(Duration.ofSeconds(20));
        songDatabase.save(song2);
        song3.setTitle("title3");
        song3.setDuration(Duration.ofSeconds(15));
        songDatabase.save(song3);


        songPlaylist1.addSong(song1);
        songPlaylist1.addSong(song2);
        songPlaylist1.addSong(song3);


        songPlaylist2.addSong(song1);

        songPlaylistDatabase.update(songPlaylist1);
        songPlaylistDatabase.update(songPlaylist2);
    }


    @Test
    void testGet() {
        SongPlaylist songPlaylist = songPlaylistDatabase.getByName(songPlaylist1.getTitle());
        SongPlaylist songPlaylist2 = songPlaylistDatabase.get(songPlaylist.getId()).orElseThrow();
        assertEquals(songPlaylist.getTitle(), songPlaylist2.getTitle());
    }

    @Test
    void testGetByName() {
        assertEquals(songPlaylist1.getTitle(), songPlaylistDatabase.getByName(songPlaylist1.getTitle()).getTitle());
        assertEquals(songPlaylist2.getTitle(), songPlaylistDatabase.getByName(songPlaylist2.getTitle()).getTitle());
    }

    @Test
    void testGetAll() {
        List<SongPlaylist> allPlaylists = songPlaylistDatabase.getAll();
        assertEquals(2, allPlaylists.size());
    }

    @Test
    void testGetAllByName() {
        SongPlaylist songPlaylist3 = new SongPlaylist();
        songPlaylist3.setTitle("playlist1");
        songPlaylistDatabase.save(songPlaylist3);
        List<SongPlaylist> foundSongPlaylists = songPlaylistDatabase.getAllByName(songPlaylist1.getTitle());
        assertEquals(2, foundSongPlaylists.size());
    }

}
