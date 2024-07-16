import dao.collections.SongPlaylistDAO;
import dao.tracks.SongDAO;
import domainmodel.entities.collections.SongPlaylist;
import domainmodel.entities.tracks.Song;
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
        SongPlaylist songPlaylist = songPlaylistDatabase.getByTitle(songPlaylist1.getTitle()).getFirst();
        SongPlaylist songPlaylist2 = songPlaylistDatabase.get(songPlaylist.getId()).orElseThrow();
        assertEquals(songPlaylist.getTitle(), songPlaylist2.getTitle());
    }

    @Test
    void testGetByName() {
        assertEquals(songPlaylist1.getTitle(), songPlaylistDatabase.getByTitle(songPlaylist1.getTitle()).getFirst().getTitle());
        assertEquals(songPlaylist2.getTitle(), songPlaylistDatabase.getByTitle(songPlaylist2.getTitle()).getFirst().getTitle());
    }

    @Test
    void testGetAll() {
        List<SongPlaylist> allPlaylists = songPlaylistDatabase.getAll();
        assertEquals(2, allPlaylists.size());
    }

    @Test
    void testGetByTitle() {
        SongPlaylist songPlaylist3 = new SongPlaylist();
        songPlaylist3.setTitle("playlist1");
        songPlaylistDatabase.save(songPlaylist3);
        List<SongPlaylist> foundSongPlaylists = songPlaylistDatabase.getByTitle(songPlaylist1.getTitle());
        assertEquals(2, foundSongPlaylists.size());
    }

}
