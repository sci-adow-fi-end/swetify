import dao.tracks.SongDAO;
import domainmodel.entities.tracks.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SongDAOTest extends BaseTest {

    SongDAO songDatabase;
    Song song1 = new Song();
    Song song2 = new Song();
    Song song3 = new Song();
    Song song4 = new Song();

    @BeforeEach
    public void setUp(){
        super.setUp();
        songDatabase = new SongDAO();

        song1.setTitle("title1");
        song1.setDuration(Duration.ofSeconds(10));
        song2.setTitle("title2");
        song2.setDuration(Duration.ofSeconds(20));
        song3.setTitle("title3");
        song3.setDuration(Duration.ofSeconds(15));
        songDatabase.save(song1);
        songDatabase.save(song2);
        songDatabase.save(song3);
    }

    @Test
    void testGet(){
        Song song = songDatabase.getByTitle(song1.getTitle()).getFirst();
        Song song2 = songDatabase.get(song.getId()).orElseThrow();
        assertEquals(song.getTitle(), song2.getTitle());
    }

    @Test
    void testGetByTitle() {

        assertEquals(song1.getTitle(), songDatabase.getByTitle(song1.getTitle()).getFirst().getTitle());
        assertEquals(song2.getTitle(), songDatabase.getByTitle(song2.getTitle()).getFirst().getTitle());
        assertEquals(song3.getTitle(), songDatabase.getByTitle(song3.getTitle()).getFirst().getTitle());

        Song song = new Song();
        song.setTitle("stio");
        song.setDuration(Duration.ofSeconds(10));

        List<Song> results = songDatabase.getByTitle(song.getTitle());
        assertEquals(0, results.size());

        song4.setTitle("title1");
        song4.setDuration(Duration.ofSeconds(15));
        songDatabase.save(song4);
        List<Song> foundSongs = songDatabase.getByTitle(song1.getTitle());
        assertEquals(2, foundSongs.size());
    }

    @Test
    void testGetAll(){
        List<Song> allSongs = songDatabase.getAll();
        assertEquals(3, allSongs.size());
        assertEquals(allSongs.get(0).getTitle(), song1.getTitle());
        assertEquals(allSongs.get(1).getTitle(), song2.getTitle());
        assertEquals(allSongs.get(2).getTitle(), song3.getTitle());
    }

}
